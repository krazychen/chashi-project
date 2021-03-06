/*
 * Copyright 2019-2029 kris
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.io.yy.util;

import java.util.UUID;

/**
 * @author kris
 * @date 2019-10-24
 */
public class UUIDUtil {

    /**
     * 采用URL Base64字符，即把“+/”换成“-_”
     */
    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_*".toCharArray();

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }


    /**
     * 将随机UUID转换成指定位数的字符串
     *
     * @param bits 指定位数
     * @return
     */
//	public static String getUUID22(String u) {
//		UUID uuid = UUID.fromString(u);
    public static String getUUIDBits(int bits) {
        UUID uuid = UUID.randomUUID();
//		System.out.println(uuid.toString());
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        char[] out = new char[24];
        int tmp = 0, idx = 0;
        // 基础写法
        /*tmp = (int) ((msb >>> 40) & 0xffffff);
        out[idx + 3] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 2] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 1] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx] = alphabet[tmp & 0x3f];
        idx += 4;
        tmp = (int) ((msb >>> 16) & 0xffffff);
        out[idx + 3] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 2] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 1] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx] = alphabet[tmp & 0x3f];
        idx += 4;
        tmp = (int) (((msb & 0xffff) << 8) | (lsb >>> 56 & 0xff));
        out[idx + 3] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 2] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 1] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx] = alphabet[tmp & 0x3f];
        idx += 4;
        tmp = (int) ((lsb >>> 32) & 0xffffff);
        out[idx + 3] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 2] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 1] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx] = alphabet[tmp & 0x3f];
        idx += 4;
        tmp = (int) ((lsb >>> 8) & 0xffffff);
        out[idx + 3] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 2] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx + 1] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx] = alphabet[tmp & 0x3f];
        idx += 4;
        tmp = (int) (lsb & 0xff);
        out[idx + 3] = alphabet[64];
        out[idx + 2] = alphabet[64];
        tmp <<= 4;
        out[idx + 1] = alphabet[tmp & 0x3f];
        tmp >>= 6;
        out[idx] = alphabet[tmp & 0x3f];
        idx += 4;*/

        // 循环写法
        int bit = 0, bt1 = 8, bt2 = 8;
        int mask = 0x00, offsetm = 0, offsetl = 0;

        for (; bit < 16; bit += 3, idx += 4) {
            offsetm = 64 - (bit + 3) * 8;
            offsetl = 0;
            tmp = 0;

            if (bt1 > 3) {
                mask = (1 << 8 * 3) - 1;
            } else if (bt1 >= 0) {
                mask = (1 << 8 * bt1) - 1;
                bt2 -= 3 - bt1;
            } else {
                mask = (1 << 8 * ((bt2 > 3) ? 3 : bt2)) - 1;
                bt2 -= 3;
            }
            if (bt1 > 0) {
                bt1 -= 3;
                tmp = (int) ((offsetm < 0) ? msb : (msb >>> offsetm) & mask);
                if (bt1 < 0) {
                    tmp <<= Math.abs(offsetm);
                    mask = (1 << 8 * Math.abs(bt1)) - 1;
                }
            }
            if (offsetm < 0) {
                offsetl = 64 + offsetm;
                tmp |= ((offsetl < 0) ? lsb : (lsb >>> offsetl)) & mask;
            }

            if (bit == 15) {
                out[idx + 3] = alphabet[64];
                out[idx + 2] = alphabet[64];
                tmp <<= 4;
            } else {
                out[idx + 3] = alphabet[tmp & 0x3f];
                tmp >>= 6;
                out[idx + 2] = alphabet[tmp & 0x3f];
                tmp >>= 6;
            }
            out[idx + 1] = alphabet[tmp & 0x3f];
            tmp >>= 6;
            out[idx] = alphabet[tmp & 0x3f];
        }

        return new String(out, 0, bits);
    }

    public static void main(String[] args) {
        System.out.println(getUUIDBits(28));
    }
}
