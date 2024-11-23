package com.be.tapchi.pjtapchi.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class GoogleTokenUtil {
    public static void decodeToken(String idToken) {
        try {
            DecodedJWT jwt = JWT.decode(idToken);

        // Trích xuất thông tin từ payload
        String userId = jwt.getSubject(); // sub
        String email = jwt.getClaim("email").asString(); // email
        String name = jwt.getClaim("name").asString(); // name

        // Hiển thị thông tin
        System.out.println("User ID (sub): " + userId);
        System.out.println("Email: " + email);
        System.out.println("Name: " + name);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Token khong hop le: "+e.getMessage());
        }
        
    }

    public static boolean isTokenExpired(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);

        // Lấy thời gian hết hạn từ trường exp
        long expirationTime = jwt.getExpiresAt().getTime() / 1000;
        long currentTime = System.currentTimeMillis() / 1000;

       // Chuyển đổi thời gian hết hạn thành LocalDateTime
        LocalDateTime expirationDateTime = Instant.ofEpochSecond(expirationTime)
                .atZone(ZoneId.systemDefault()) // Sử dụng múi giờ hệ thống
                .toLocalDateTime();

        // Chuyển đổi thời gian hiện tại thành LocalDateTime
        LocalDateTime currentDateTime = Instant.ofEpochSecond(currentTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Định dạng ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // In ra kết quả
        System.out.println("Thời gian hết hạn: " + expirationDateTime.format(formatter));
        System.out.println("Thời gian hiện tại: " + currentDateTime.format(formatter));
        
        // Kiểm tra nếu token đã hết hạn
        return expirationTime > currentTime;
    }

    public static String getSubFromTokenGoogle(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

        // Trích xuất thông tin từ payload
        String userId = jwt.getSubject(); // sub
        //String email = jwt.getClaim("email").asString(); // email
        //String name = jwt.getClaim("name").asString(); // name

        // Hiển thị thông tin
        //System.out.println("User ID (sub): " + userId);
        return userId;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Token khong hop le: "+e.getMessage());
            return null;
        }
    }



    public static String getPictureFromTokenGoogle(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

        // Trích xuất thông tin từ payload
        //String userId = jwt.getSubject(); // sub
        //String email = jwt.getClaim("email").asString(); // email
        String name = jwt.getClaim("picture").asString(); // name

        // Hiển thị thông tin
        //System.out.println("User ID (sub): " + userId);
        return name;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Token khong hop le: "+e.getMessage());
            return null;
        }
    }

    public static String getNameFromTokenGoogle(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

        // Trích xuất thông tin từ payload
        //String userId = jwt.getSubject(); // sub
        //String email = jwt.getClaim("email").asString(); // email
        String name = jwt.getClaim("name").asString(); // name

        // Hiển thị thông tin
        //System.out.println("User ID (sub): " + userId);
        return name;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Token khong hop le: "+e.getMessage());
            return null;
        }
    }

    public static boolean getVerifiedEmailFromTokenGoogle(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

        // Trích xuất thông tin từ payload
        //String userId = jwt.getSubject(); // sub
        //String email = jwt.getClaim("email").asString(); // email
        Boolean name = jwt.getClaim("email_verified").asBoolean(); // name

        // Hiển thị thông tin
        //System.out.println("User ID (sub): " + userId);
        return name;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Token khong hop le: "+e.getMessage());
            return false;
        }
    }

    public static String getEmailFromTokenGoogle(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);

        // Trích xuất thông tin từ payload
        //String userId = jwt.getSubject(); // sub
        String email = jwt.getClaim("email").asString(); // email
        //String name = jwt.getClaim("name").asString(); // name

        // Hiển thị thông tin
        //System.out.println("User ID (sub): " + userId);
        return email;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Token khong hop le: "+e.getMessage());
            return null;
        }
    }

    

    public static void main(String[] args) {
        //System.out.println(isTokenExpired("eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NzQwYTcwYjA5NzJkY2NmNzVmYTg4YmM1MjliZDE2YTMwNTczYmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTMzOTMyMjgxMzMxOTA5NzE2NzAiLCJlbWFpbCI6InR2dWdpYW5nQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3MzIyNTc4NDYsIm5hbWUiOiJWxakgR2lhbmcgVHLhuqduIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0tYRnRUTmVvbVJaWFJZZ1g0RzVNcGdWU2lwYUItdzhMWHFwaEU0SWRJcG1xQk15UzJZPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlbFqSBHaWFuZyIsImZhbWlseV9uYW1lIjoiVHLhuqduIiwiaWF0IjoxNzMyMjU4MTQ2LCJleHAiOjE3MzIyNjE3NDYsImp0aSI6Ijg0ZGYyNjRhZGNkNzEwNzczZDkwYzdkNTdmZWE5MDVkNTcxMmIyNzEifQ.Qo4adLkYsZNNIK9MdSjsZc8fJNL4VvQFgyocS4FVfiAhzxGTlgsEBtEwXHi6O6WnBLrk9VAE6Az01j3KDgZv9oAFcWdeDP_maarN0_vw8suS-RZdbHl6V88LESokK8S9B2kwjbddP8McWX702HpF0hp-8qq6oUQHuf6qk5JJ4rMxNrxapNI9wDvhMTSpZviLT7D-bmAvleeYzaRMI-_-k01iGGa2YlHEgcqHQCwyPrxI9QVeqiY3aeS_VsEbq__7aBJOmBnezihXXrx9Dxw2zjb5LeCXyexl2i0qfjQLVxSPz3cUa4Bjo31yIAeHHMuWgWG4GGEgIsNq9ea9zLj94w"));
        //("eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NzQwYTcwYjA5NzJkY2NmNzVmYTg4YmM1MjliZDE2YTMwNTczYmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDQ1OTczMTM3NjI1MjgwMTAwOTYiLCJlbWFpbCI6Im5nb2N6aWcxMUBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmJmIjoxNzMyMjQ5MzI0LCJuYW1lIjoiemlnMTEgbmdvYyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NKWTV0NjBCNXM3Y0lqVzZJcWYwN1BCeG11UWZOVkFtdkdDTzJIbjIxX3pQQWhldy0wPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6InppZzExIiwiZmFtaWx5X25hbWUiOiJuZ29jIiwiaWF0IjoxNzMyMjQ5NjI0LCJleHAiOjE3MzIyNTMyMjQsImp0aSI6IjZjYTZhNGJmNjljZjI0OGJmMTkwZDZhZDE4MzAzYjIyNDhlYzBmMWMifQ.LCHSQlOfTEdXS31Yh8zloCZBS-lR-a75YC29j0v2Pkxr0Iw1htMIgHsp0ubKm0BH4mZobHB6PgsdxG9u1HXDBE6hFe1KeGFpA-SrYc_zA6nM88bpulX4W7W_NSshdrjmpukUEqyUFaIanNv4KSlnJV2VxJgN5a22FEhOuohORrcb-yBjZleLWNGqSwRfzwNr7memnsaSgT9PJdmx7hC-JBxJNXa-vc9s_dFwS8mroFOJx3NnUAgVjXaKgBXDwhMUlb9e01TfaaMeiKlPgaYvsLuagpOG_1FfMGr1GaUzP1R5Y_XP5Yzr8guKg3ccrRv1P92Zk3V05DPct2_E1Cz4vQ");
        System.out.println(getEmailFromTokenGoogle("eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NzQwYTcwYjA5NzJkY2NmNzVmYTg4YmM1MjliZDE2YTMwNTczYmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTMzOTMyMjgxMzMxOTA5NzE2NzAiLCJlbWFpbCI6InR2dWdpYW5nQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3MzIyNTc4NDYsIm5hbWUiOiJWxakgR2lhbmcgVHLhuqduIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0tYRnRUTmVvbVJaWFJZZ1g0RzVNcGdWU2lwYUItdzhMWHFwaEU0SWRJcG1xQk15UzJZPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlbFqSBHaWFuZyIsImZhbWlseV9uYW1lIjoiVHLhuqduIiwiaWF0IjoxNzMyMjU4MTQ2LCJleHAiOjE3MzIyNjE3NDYsImp0aSI6Ijg0ZGYyNjRhZGNkNzEwNzczZDkwYzdkNTdmZWE5MDVkNTcxMmIyNzEifQ.Qo4adLkYsZNNIK9MdSjsZc8fJNL4VvQFgyocS4FVfiAhzxGTlgsEBtEwXHi6O6WnBLrk9VAE6Az01j3KDgZv9oAFcWdeDP_maarN0_vw8suS-RZdbHl6V88LESokK8S9B2kwjbddP8McWX702HpF0hp-8qq6oUQHuf6qk5JJ4rMxNrxapNI9wDvhMTSpZviLT7D-bmAvleeYzaRMI-_-k01iGGa2YlHEgcqHQCwyPrxI9QVeqiY3aeS_VsEbq__7aBJOmBnezihXXrx9Dxw2zjb5LeCXyexl2i0qfjQLVxSPz3cUa4Bjo31yIAeHHMuWgWG4GGEgIsNq9ea9zLj94w"));
        System.out.println(getPictureFromTokenGoogle("eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NzQwYTcwYjA5NzJkY2NmNzVmYTg4YmM1MjliZDE2YTMwNTczYmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTMzOTMyMjgxMzMxOTA5NzE2NzAiLCJlbWFpbCI6InR2dWdpYW5nQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3MzIyNTc4NDYsIm5hbWUiOiJWxakgR2lhbmcgVHLhuqduIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0tYRnRUTmVvbVJaWFJZZ1g0RzVNcGdWU2lwYUItdzhMWHFwaEU0SWRJcG1xQk15UzJZPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlbFqSBHaWFuZyIsImZhbWlseV9uYW1lIjoiVHLhuqduIiwiaWF0IjoxNzMyMjU4MTQ2LCJleHAiOjE3MzIyNjE3NDYsImp0aSI6Ijg0ZGYyNjRhZGNkNzEwNzczZDkwYzdkNTdmZWE5MDVkNTcxMmIyNzEifQ.Qo4adLkYsZNNIK9MdSjsZc8fJNL4VvQFgyocS4FVfiAhzxGTlgsEBtEwXHi6O6WnBLrk9VAE6Az01j3KDgZv9oAFcWdeDP_maarN0_vw8suS-RZdbHl6V88LESokK8S9B2kwjbddP8McWX702HpF0hp-8qq6oUQHuf6qk5JJ4rMxNrxapNI9wDvhMTSpZviLT7D-bmAvleeYzaRMI-_-k01iGGa2YlHEgcqHQCwyPrxI9QVeqiY3aeS_VsEbq__7aBJOmBnezihXXrx9Dxw2zjb5LeCXyexl2i0qfjQLVxSPz3cUa4Bjo31yIAeHHMuWgWG4GGEgIsNq9ea9zLj94w"));
        System.out.println(getNameFromTokenGoogle("eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NzQwYTcwYjA5NzJkY2NmNzVmYTg4YmM1MjliZDE2YTMwNTczYmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTMzOTMyMjgxMzMxOTA5NzE2NzAiLCJlbWFpbCI6InR2dWdpYW5nQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3MzIyNTc4NDYsIm5hbWUiOiJWxakgR2lhbmcgVHLhuqduIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0tYRnRUTmVvbVJaWFJZZ1g0RzVNcGdWU2lwYUItdzhMWHFwaEU0SWRJcG1xQk15UzJZPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlbFqSBHaWFuZyIsImZhbWlseV9uYW1lIjoiVHLhuqduIiwiaWF0IjoxNzMyMjU4MTQ2LCJleHAiOjE3MzIyNjE3NDYsImp0aSI6Ijg0ZGYyNjRhZGNkNzEwNzczZDkwYzdkNTdmZWE5MDVkNTcxMmIyNzEifQ.Qo4adLkYsZNNIK9MdSjsZc8fJNL4VvQFgyocS4FVfiAhzxGTlgsEBtEwXHi6O6WnBLrk9VAE6Az01j3KDgZv9oAFcWdeDP_maarN0_vw8suS-RZdbHl6V88LESokK8S9B2kwjbddP8McWX702HpF0hp-8qq6oUQHuf6qk5JJ4rMxNrxapNI9wDvhMTSpZviLT7D-bmAvleeYzaRMI-_-k01iGGa2YlHEgcqHQCwyPrxI9QVeqiY3aeS_VsEbq__7aBJOmBnezihXXrx9Dxw2zjb5LeCXyexl2i0qfjQLVxSPz3cUa4Bjo31yIAeHHMuWgWG4GGEgIsNq9ea9zLj94w"));
        System.out.println(getVerifiedEmailFromTokenGoogle("eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NzQwYTcwYjA5NzJkY2NmNzVmYTg4YmM1MjliZDE2YTMwNTczYmQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIyNjk2NzUyNzYzNjYtY2drN2piNXFmbzczazJkcGptZTBqYnFsZjkxZnYwaDAuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTMzOTMyMjgxMzMxOTA5NzE2NzAiLCJlbWFpbCI6InR2dWdpYW5nQGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYmYiOjE3MzIyNTc4NDYsIm5hbWUiOiJWxakgR2lhbmcgVHLhuqduIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS9hL0FDZzhvY0tYRnRUTmVvbVJaWFJZZ1g0RzVNcGdWU2lwYUItdzhMWHFwaEU0SWRJcG1xQk15UzJZPXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6IlbFqSBHaWFuZyIsImZhbWlseV9uYW1lIjoiVHLhuqduIiwiaWF0IjoxNzMyMjU4MTQ2LCJleHAiOjE3MzIyNjE3NDYsImp0aSI6Ijg0ZGYyNjRhZGNkNzEwNzczZDkwYzdkNTdmZWE5MDVkNTcxMmIyNzEifQ.Qo4adLkYsZNNIK9MdSjsZc8fJNL4VvQFgyocS4FVfiAhzxGTlgsEBtEwXHi6O6WnBLrk9VAE6Az01j3KDgZv9oAFcWdeDP_maarN0_vw8suS-RZdbHl6V88LESokK8S9B2kwjbddP8McWX702HpF0hp-8qq6oUQHuf6qk5JJ4rMxNrxapNI9wDvhMTSpZviLT7D-bmAvleeYzaRMI-_-k01iGGa2YlHEgcqHQCwyPrxI9QVeqiY3aeS_VsEbq__7aBJOmBnezihXXrx9Dxw2zjb5LeCXyexl2i0qfjQLVxSPz3cUa4Bjo31yIAeHHMuWgWG4GGEgIsNq9ea9zLj94w"));
    }
}
