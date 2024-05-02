package community.server.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.Getter;
import lombok.Setter;

public class PasswordEncryption {

  @Getter
  @Setter
  private static byte[] hashedPassword;

  public static byte[] hashPassword(String passwordToHash) {
    try {
      // SHA-256 MessageDigest 인스턴스 생성
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      // 비밀번호의 바이트 배열을 해시하여 업데이트
      md.update(passwordToHash.getBytes());
      setHashedPassword(md.digest());
      // 해시 계산을 완료하고 바이트 배열로 반환
      // 바이트 배열을 16진수 문자열로 변환
      return md.digest();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static boolean verifyPassword(byte[] storedPasswordHash, String passwordToVerify) {
    // 사용자가 입력한 비밀번호를 해시
    byte[] hashedPassword = hashPassword(passwordToVerify);
    // 해시된 비밀번호와 저장된 해시를 비교
    return MessageDigest.isEqual(storedPasswordHash, hashedPassword);
  }
}
