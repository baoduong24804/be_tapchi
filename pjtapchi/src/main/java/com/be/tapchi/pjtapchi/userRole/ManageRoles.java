package com.be.tapchi.pjtapchi.userRole;

public class ManageRoles {
    public static String getCUSTOMERRole(){
        try {
            return RoleName.CUSTOMER.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
    public static String getADMINRole(){
        try {
            return RoleName.ADMIN.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
    public static String getAUTHORRole(){
        try {
            return RoleName.AUTHOR.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
    public static String getCENSORRole(){
        try {
            return RoleName.CENSOR.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
    public static String getEDITORRole(){
        try {
            return RoleName.EDITOR.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
    public static String getPARTNERRole(){
        try {
            return RoleName.PARTNER.toString();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
        
    }
}
