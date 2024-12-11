package com.be.tapchi.pjtapchi.ordercontext;

import org.springframework.web.context.annotation.RequestScope;

@RequestScope
public class OrderContext {
    private static final ThreadLocal<Long> hoadonId = new ThreadLocal<>();
    private static final ThreadLocal<Integer> hopdongId = new ThreadLocal<>();
    private static final ThreadLocal<Long> taikhoanId = new ThreadLocal<>();

    public static Long getHoadonId() {
        return hoadonId.get();
    }

    public static void setHoadonId(Long id) {
        hoadonId.set(id);
    }

    public static Integer getHopdongId() {
        return hopdongId.get();
    }

    public static void setHopdongId(Integer id) {
        hopdongId.set(id);
    }

    public static Long getTaikhoanId() {
        return taikhoanId.get();
    }

    public static void setTaikhoanId(Long id) {
        taikhoanId.set(id);
    }

    public static void clear() {
        hoadonId.remove();
        hopdongId.remove();
        taikhoanId.remove();
    }
}