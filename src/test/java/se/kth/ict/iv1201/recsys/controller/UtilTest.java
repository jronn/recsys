/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package se.kth.ict.iv1201.recsys.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import se.kth.ict.iv1201.recsys.model.RecSysUtil;

/**
 *
 * @author Josef
 */
public class UtilTest {
    
    @Test
    public void testHash() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        assertEquals("Hash is invalid with SHA-256 standard", "36bbe50ed96841d10443bcb670d6554f0a34b761be67ec9c4a8ad2c0c44ca42c", RecSysUtil.hashText("abcde"));
        assertEquals("Hash is invalid with SHA-256 standard", "f18244a0bb338152e57638b4ece6d5e72142a055be3b2fdac954d9429adfb7ec", RecSysUtil.hashText("123eee"));
        assertEquals("Hash is invalid with SHA-256 standard", "ebe2e5eb5029b8de9db170270ccc0de4e6b50bf3433668759600a41e8cb6bf39", RecSysUtil.hashText("cvbncvn"));
        assertEquals("Hash is invalid with SHA-256 standard", "223db1a2d25a277de751396d76e8270466aaaccad7e8155e7ae8c21732bb8343", RecSysUtil.hashText("@asASD\"542"));
        assertEquals("Hash is invalid with SHA-256 standard", "949940b71d9bf2e72ccddd05d7508f2e60871acaceb9af73513e76aae12a79b5", RecSysUtil.hashText("\"¤\"¤!¤!¤\""));
    }
}
