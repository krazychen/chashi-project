package com.io.yy.core.handler;

import com.baomidou.mybatisplus.core.parser.AbstractJsqlParser;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;

import java.util.List;

/**
 * @author kris
 * @ClassName MyTenantSqlParser
 * @Description TODO
 * @date 07/30
 */
@Data
@Slf4j
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MyTenantSqlParser extends AbstractJsqlParser {


    private MyTenantHandler tenantHandler;

    /**
     * select body 语句处理
     */
    public void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            this.processPlainSelect((PlainSelect)selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem)selectBody;
            if (withItem.getSelectBody() != null) {
                this.processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList)selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                operationList.getSelects().forEach(this::processSelectBody);
            }
        }

    }


    /**
     * <p>
     * insert 语句处理
     * </p>
     */
    public void processInsert(Insert insert) {
        if(this.tenantHandler.doUserFilter()){
            if (this.tenantHandler.doTableFilter(insert.getTable().getName())) {
                insert.getColumns().add(new Column(this.tenantHandler.getTenantIdColumn()));
                if (insert.getSelect() != null) {
                    this.processPlainSelect((PlainSelect)insert.getSelect().getSelectBody(), true);
                } else {
                    if (insert.getItemsList() == null) {
                        throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId", new Object[0]);
                    }

                    ItemsList itemsList = insert.getItemsList();
                    if (itemsList instanceof MultiExpressionList) {
                        ((MultiExpressionList)itemsList).getExprList().forEach((el) -> {
                            el.getExpressions().add(this.tenantHandler.getTenantId(false));
                        });
                    } else {
                        ((ExpressionList)insert.getItemsList()).getExpressions().add(this.tenantHandler.getTenantId(false));
                    }
                }

            }
        }

    }

    /**
     * <p>
     * update 语句处理
     * </p>
     */
    public void processUpdate(Update update) {
        Table table = update.getTable();
        if(this.tenantHandler.doUserFilter()){
            if (this.tenantHandler.doTableFilter(table.getName())) {
                update.setWhere(this.andExpression(table, update.getWhere()));
            }
        }
    }

    /**
     * <p>
     * delete 语句处理
     * </p>
     */
    @Override
    public void processDelete(Delete delete) {
        if(this.tenantHandler.doUserFilter()){
            if (this.tenantHandler.doTableFilter(delete.getTable().getName())) {
                delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere()));
            }
        }
    }

    /**
     * <p>
     * delete update 语句 where 处理
     * </p>
     */
    protected BinaryExpression andExpression(Table table, Expression where) {

        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(this.getAliasColumn(table));
        equalsTo.setRightExpression(this.tenantHandler.getTenantId(false));
        if (null != where) {
            return where instanceof OrExpression ? new AndExpression(equalsTo, new Parenthesis(where)) : new AndExpression(equalsTo, where);
        } else {
            return equalsTo;
        }
    }

    /**
     * <p>
     * 处理 PlainSelect
     * </p>
     */
    protected void processPlainSelect(PlainSelect plainSelect) {
        this.processPlainSelect(plainSelect, false);
    }

    /**
     * <p>
     * 处理 PlainSelect
     * </p>
     *
     * @param plainSelect
     * @param addColumn   是否添加租户列,insert into select语句中需要
     */
    protected void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table)fromItem;
            if(this.tenantHandler.doUserFilter()){
                if (this.tenantHandler.doTableFilter(fromTable.getName())) {
                    plainSelect.setWhere(this.builderExpression(plainSelect.getWhere(), fromTable));
                    if (addColumn) {
                        plainSelect.getSelectItems().add(new SelectExpressionItem(new Column(this.tenantHandler.getTenantIdColumn())));
                    }
                }
            }
        } else {
            this.processFromItem(fromItem);
        }

        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach((j) -> {
                this.processJoin(j);
                this.processFromItem(j.getRightItem());
            });
        }
    }

    /**
     * 处理子查询等
     */
    protected void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin)fromItem;
            if (subJoin.getJoinList() != null) {
                subJoin.getJoinList().forEach(this::processJoin);
            }

            if (subJoin.getLeft() != null) {
                this.processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect)fromItem;
            if (subSelect.getSelectBody() != null) {
                this.processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            this.logger.debug("Perform a subquery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect)fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    this.processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * 处理联接语句
     */
    protected void processJoin(Join join) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table)join.getRightItem();
//            if(!this.tenantHandler.doUserFilter()){
//                if (!this.tenantHandler.doTableFilter(fromTable.getName())) {
//                    return;
//                }
//            }
//            join.setOnExpression(this.builderExpression(join.getOnExpression(), fromTable));
            if(this.tenantHandler.doUserFilter()){
                if (this.tenantHandler.doTableFilter(fromTable.getName())) {
                    join.setOnExpression(this.builderExpression(join.getOnExpression(), fromTable));
                }
            }
        }
    }

    /**
     * 处理条件
     */
    protected Expression builderExpression(Expression currentExpression, Table table) {
        Expression tenantExpression = this.tenantHandler.getTenantId(true);
        Expression appendExpression = this.processTableAlias4CustomizedTenantIdExpression(tenantExpression, table);
        if (currentExpression == null) {
            return appendExpression;
        } else {
            if (currentExpression instanceof BinaryExpression) {
                BinaryExpression binaryExpression = (BinaryExpression)currentExpression;
                this.doExpression(binaryExpression.getLeftExpression());
                this.doExpression(binaryExpression.getRightExpression());
            } else if (currentExpression instanceof InExpression) {
                InExpression inExp = (InExpression)currentExpression;
                ItemsList rightItems = inExp.getRightItemsList();
                if (rightItems instanceof SubSelect) {
                    this.processSelectBody(((SubSelect)rightItems).getSelectBody());
                }
            }

            return currentExpression instanceof OrExpression ? new AndExpression(new Parenthesis(currentExpression), appendExpression) : new AndExpression(currentExpression, appendExpression);
        }
    }

    protected void doExpression(Expression expression) {
        if (expression instanceof FromItem) {
            this.processFromItem((FromItem)expression);
        } else if (expression instanceof InExpression) {
            InExpression inExp = (InExpression)expression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                this.processSelectBody(((SubSelect)rightItems).getSelectBody());
            }
        }

    }

    protected Expression processTableAlias4CustomizedTenantIdExpression(Expression expression, Table table) {
        Object target;
        if (expression instanceof ValueListExpression) {
            InExpression inExpression = new InExpression();
            inExpression.setLeftExpression(this.getAliasColumn(table));
            inExpression.setRightItemsList(((ValueListExpression)expression).getExpressionList());
            target = inExpression;
        } else {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(this.getAliasColumn(table));
            equalsTo.setRightExpression(expression);
            target = equalsTo;
        }

        return (Expression)target;
    }

    /**
     * <p>
     * 租户字段别名设置<br>
     * tableName.tenantId 或 tableAlias.tenantId
     * </p>
     *
     * @param table 表对象
     * @return 字段
     */
    protected Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (table.getAlias() != null) {
            column.append(table.getAlias().getName()).append(".");
        }

        column.append(this.tenantHandler.getTenantIdColumn());
        return new Column(column.toString());
    }

    public MyTenantHandler getTenantHandler() {
        return this.tenantHandler;
    }

    public MyTenantSqlParser setTenantHandler(final MyTenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
        return this;
    }

    public String toString() {
        return "TenantSqlParser(tenantHandler=" + this.getTenantHandler() + ")";
    }

    public MyTenantSqlParser() {
    }

    public MyTenantSqlParser(final MyTenantHandler tenantHandler) {
        this.tenantHandler = tenantHandler;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MyTenantSqlParser)) {
            return false;
        } else {
            MyTenantSqlParser other = (MyTenantSqlParser)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (!super.equals(o)) {
                return false;
            } else {
                Object this$tenantHandler = this.getTenantHandler();
                Object other$tenantHandler = other.getTenantHandler();
                if (this$tenantHandler == null) {
                    if (other$tenantHandler != null) {
                        return false;
                    }
                } else if (!this$tenantHandler.equals(other$tenantHandler)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TenantSqlParser;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = super.hashCode();
        Object $tenantHandler = this.getTenantHandler();
        result = result * 59 + ($tenantHandler == null ? 43 : $tenantHandler.hashCode());
        return result;
    }
}
