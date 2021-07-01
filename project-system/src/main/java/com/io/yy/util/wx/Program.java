package com.io.yy.util.wx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class Program {

	public static void main(String[] args) throws Exception {

		//
		// 第三方回复公众平台
		//

		// 需要加密的明文
		String encodingAesKey = "Vz89EhBo7YBZLPtxn4grsUxkcwkRIK6BLXmaQrhf6MV";
		String token = "Cv0s88n7Lff8Kh7RfiiGoaaQNots1t9v";
		String timestamp = "1590165155";
		String nonce = "988138209";
		String appId = "wx249bea207743b5c0";
		String replyMsg = "<xml>" +
				"<Encrypt><![CDATA[ZAj8JNeyaLdnp3hQOfEW4IUoTr2sUSu6lNB5Ksun3B0GCkS5l9bsK6cl2vx3KQ5GphrsdBlUqXz8WAdbN0iU9z9ARtt2ddbnbrafBQw4WyttjECfDGjiXYSwNJiNbvRfXJyn1hVjFiCxAtsp1c3XUSF33j1OtNEuFQWS4VriiC4zPvtAAo75fVJSuLTmntbOGIfw9UJUwxt9VMkIhowfmmvPHWg9CYHPEroGcqNkQ0IT20LIZO0dlWFTGmpINsGjAqnSxwnr1vx+eGSlh9kCxsgq34TUYX4eZtBSEy5lmyn+neUIHxfIA2cuRWEZmAYAtrMn13as6JYTLKaXawGDOYDrgeXhb/8vj37E61hwFmvU+IBienDyY+UimsGyT8dXueYnNUDuGWac2jRJeSVxl2AzCI13J/qeGGO3xx4XORIKMDQpxkEBVspD8FUTAm33MGhCX+8ubsFrUFc9apUYXM+mtyo9/97KHaowPXL/FP5tDwcqkAVlvAffsyLUL8GLjfzInuztouUdhHib6mphd58sUCRRHBtp6cJVzY9qlPj2pFZif1Bb3mqa4qGKraBnQG3hA4J7zxOxBBHnJnQ/3dKoBc0q0k/bJDYGH1BnbXWf/RfzBykONIeuSa4aV/hyEQSrAgdx3kB1Gk/I89J0NHR3nxSOrRrnQBBtPmP/4NPcZrXICzGPFiD+OSrtbFekHRtHX/StpP93LHY6QKbCRA==]]></Encrypt>\n" +
				"<MsgSignature><![CDATA[14ccb8ce36e0c6b6ddd32c4f1478e44946c499d1]]></MsgSignature>" +
				"<TimeStamp>1590165155</TimeStamp>" +
				"<Nonce><![CDATA[988138209]]></Nonce>" +
				"</xml>";

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
/*		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);*/

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(replyMsg);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();

		String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
		String fromXML = String.format(format, encrypt);

		//
		// 公众平台发送消息给第三方，第三方处理
		//

		// 第三方收到公众号平台发送的消息
		String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		System.out.println("解密后明文: " + result2);
		
		//pc.verifyUrl(null, null, null, null);
	}
}
