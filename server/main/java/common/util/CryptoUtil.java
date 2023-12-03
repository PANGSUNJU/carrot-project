package common.util;


import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author 서영석
 * @since 2021.10.31
 *
 * 암호화 관련 모듈 Class 입니다.
 */
public class CryptoUtil {

    /**
     * @author 서영석
     * @since 2021.11.12
     *
     * session에 RSA 암호화 Key생성 후, 공개키 반환 (무조건 새로 생성)
     */
    public static PublicKey getPublicKeyInSession () {
        try {
            //현재 client의 HttpSession 조회
            HttpSession session = CommonUtil.getHttpSession(true);

            /** session이 존재하지 않을 때 **/
            if(session == null) {
                return null;
            }/** session이 존재할 때 **/ else {
                //rsaKey 생성
                KeyPair keyPair = genRsaKeyPair();
                //session에 rsaKey 담기
                session.setAttribute("rsaKey", keyPair);
                //공개키 return
                return keyPair.getPublic();
            }
        } catch(Exception e) {
            System.out.println("AuthUtil getPublicKeyInSession Error : ");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author 서영석
     * @since 2021.11.12
     *
     * session에 담겨진 RSA 개인키 반환 (session에서 암호화 Key 무조건 삭제)
     */
    public static PrivateKey getPrivateKeyInSession () {
        try {
            //현재 client의 HttpSession 조회
            HttpSession session = CommonUtil.getHttpSession(false);

            /** session이 존재하지 않을 때 **/
            if(session == null) {
                return null;
            }/** session이 존재할 때 **/ else {
                //session에 있는 rsaKey 가지고오기
                KeyPair keyPair = (KeyPair) session.getAttribute("rsaKey");
                //session에 있는 rsaKey 삭제
                deleteRsaKeyInSession();
                //공개키 return
                return keyPair.getPrivate();
            }
        } catch(Exception e) {
            System.out.println("AuthUtil getPrivateKeyInSession Error : ");
            e.printStackTrace();
            return null;
        }
    }

    /*
     * @author 서영석
     * @since 2021.11.12
     *
     * session에 RSA암호화 Key를 반환함.
     *
     * @param isNewCreateRsaKey : 새로운 암호화 Key를 만들지에 대한 여부
     *  - true : 암호화키 새로 생성 후, 생성된 암호화키 반환 (단, session이 존재할 때만 생성)
     *  - false : 새로 생성하지 않고 기존에 생성되어있는 암호화키 반환
    public static KeyPair getRsaKeyInSession (boolean isNewCreateRsaKey) {
        try {
            ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = servletRequestAttribute.getRequest().getSession(false);

            *//** session이 존재하지 않을 때 **//* if(session == null) {
                return null;
            }*//** session이 존재할 때 **//* else {
                *//** 새로운 암호화키 생성 (O) **//* if (isNewCreateRsaKey == true) {
                    //암호화키 생성 후, session에 담기
                    session.setAttribute("rsaKey", genRsaKeyPair());
                }*//** 새로운 암호화키 생성 (X) **//* else {
                    //만약에 기존 암호화키가 없으면 새로 생성
                    if (session.getAttribute("rsaKey") == null) {
                        session.setAttribute("rsaKey", genRsaKeyPair());
                    }
                }
                //session에 담겨있는 rsaKey return
                return (KeyPair) session.getAttribute("rsaKey");
            }
        } catch(Exception e) {
            System.out.println("AuthUtil getRsaKeyInSession Error : ");
            e.printStackTrace();
            return null;
        }
    }*/

    /**
     * @author 서영석
     * @since 2021.11.12
     *
     * session에 RSA암호화 Key를 제거
     */
    public static void deleteRsaKeyInSession () {
        try {
            //현재 client의 HttpSession 조회
            HttpSession session = CommonUtil.getHttpSession(false);

            /** session에 RSA암호화 Key가 존재할 때 **/
            if(session != null && session.getAttribute("rsaKey") != null) {
                session.removeAttribute("rsaKey");
            }
        } catch(Exception e) {
            System.out.println("AuthUtil deleteRsaKeyInSession Error : ");
            e.printStackTrace();
        }
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * RSA 암호화 키쌍(공개키, 개인키) 생성 기능
     */
    public static KeyPair genRsaKeyPair() {
        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        gen.initialize(1024, new SecureRandom());
        return gen.genKeyPair();
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * RSA 공개키 -> String
     */
    public static String publicKeyToString (PublicKey publicKey) {
        return new String(byteToHex(publicKey.getEncoded()));
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * RSA 개인키 -> String
     */
    public static String privateKeyToString (PrivateKey privateKey) {
        return new String(byteToHex(privateKey.getEncoded()));
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * RSA String -> 공개키
     */
    public static PublicKey stringToPublicKey (String publicKeyStr) {
        PublicKey publicKey = null;
        try {
            //문자열을 공개키로 표현하기 위한 스펙 객체
            X509EncodedKeySpec ukeySpec = new X509EncodedKeySpec(hexToByte(publicKeyStr));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(ukeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * RSA String -> 개인키
     */
    public static PrivateKey stringToPrivateKey(String privateKeyStr){
        PrivateKey privateKey = null;
        try{
            //문자열을 개인키로 표현하기 위한 스펙 객체
            PKCS8EncodedKeySpec rkeySpec = new PKCS8EncodedKeySpec(hexToByte(privateKeyStr));
            KeyFactory rkeyFactory = KeyFactory.getInstance("RSA");
            privateKey = rkeyFactory.generatePrivate(rkeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * 공개키 Key로 text를 RSA 암호화
     */
    public static String encodingByRsa (String text, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(bytePlain);
    }

    /**
     * @author 서영석
     * @since 2021.11.08
     *
     * 개인키 Key로 text를 RSA 복호화
     */
    public static String decodingByRsa (String encodedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(encodedText.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        return new String(bytePlain, "utf-8");
    }


    /**
     * @author 서영석
     * @since 2021.10.31
     *
     * SHA-256 암호화 기능
     */
    public static String encodingBySha256 (String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        return byteToHex(md.digest());
    }


    /**
     * @author 서영석
     * @since 2021.10.31
     *
     * 바이너리 -> 16진수 변경
     */
    public static String byteToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for(byte b : data) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * @author 서영석
     * @since 2021.10.31
     *
     * 16진수 -> 바이너리 변경
     */
    public static byte[] hexToByte(String hex) {
        byte[] ba = new byte[hex.length() / 2];
        for (int i = 0; i < ba.length; i++) {
            ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return ba;
    }
}
