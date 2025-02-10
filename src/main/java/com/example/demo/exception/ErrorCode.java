package com.example.demo.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    CODE_NOT_FOUND(400, "Mã không tồn tại"),
    USER_EXIST(400, "Email đã tồn tại"),
    USER_EMAIL_EMPTY(400, "Không được để trống email"),
    USER_PASSWORD_EMPTY(400, "Không được để trống password"),
    SERVER_ERR(500, "Lỗi server"),
    USER_PASSWORD_WRONG(400,"Mật khẩu không đúng"),
    TOKEN_INVALID(401, "Token không hợp lệ"),
    USER_NOTFOUND(400, "Email không tồn tại"),
    USER_CONFIRM_PASSWORD(400,"Xác nhận mật khẩu không giống mật khẩu"),
    TOKEN_TIME_EXPIRATION(401, "Token hết hạn"),
    USER_OLD_PASSWORD(400, "Mật khẩu cũ không đúng"),
    ADDRESS_NAME(400, "Tên địa chỉ không dc để trống"),
    ADDRESS_PROVINCE_EMPTY(400, "Tên tỉnh không được để trống"),
    ADDRESS_DISTRICT_EMPTY(400, "Tên huyện không được để trống"),
    ADDRESS_WARD_EMPTY(400, "Tên xã không được để trống"),
    ADDRESS_NAME_RECEIVER_EMPTY(400, "Tên người nhận không được để trống"),
    ADDRESS_PHONE_EMPTY(400, "Số điện thoại không được để trống"),
    ADDRESS_DETAILS_EMPTY(400, "Chi tiết địa chỉ không được để trống"),
    ADDRESS_INVALID(400, "Bạn không có địa chị này"),
    CUSTOMER_NOTFOUND(400, "Không tìm thấy người dùng"),
    INVAlID_ID(400, "ID không hợp lệ"),
    EMAIL_INVALID(400,"Email không hợp lệ"),
    PASSWORD_INVALID(400, "Mật khẩu không hợp lệ"),
    INVALID_CODE(400, "Mã xác thực không hợp lệ"),
    INVALID_PASSWORD(400, "Mật khẩu không hợp lệ"),
    DUPLICATE_PASSWORD(400,"Mật khẩu mới trùng với mật khẩu cũ"),
    USER_NOT_FOUND(400, "Người dùng không tồn tại"),
    INCORRECT_OLD_PASSWORD(400, "Mật khẩu cũ không chính xác"),
    NEW_PASSWORD_MISMATCH(400, "Mật khẩu mới và xác nhận không khớp"),
    PHONE_INVALID(400, "Số điện thoại không hợp lệ"),
    CATEGORY_NAME_EMPTY(400, "Tên danh mục không được để trống"),
    CATEGORY_ALREADY_EXISTS(400, "Tên danh mục đã tồn tại"),
    INVALID_CATEGORY_STATUS(400, "Trạng thái danh mục không hợp lệ"),
    INVALID_CATEGORY_DESC(400, "Giá trị mô tả danh mục không hợp lệ"),
    PARENT_CATEGORY_NOT_FOUND(400, "Danh mục cha không tồn tại"),
    CATEGORY_NOT_FOUND(404, "Category not found"),
    REPORT_NOT_FOUND(404, "Không tìm thấy báo cáo"),
    REPORT_CREATION_FAILED(500, "Lỗi khi tạo báo cáo"),
    REPORT_UPDATE_FAILED(500, "Lỗi khi cập nhật báo cáo"),
    REPORT_DELETE_FAILED(500, "Lỗi khi xóa báo cáo"),
    Voucher_Exist(400, "Voucher Đã tồn tại"),
    Voucher_NotFound(400, "không tìm thấy Voucher"),
    ORDER_Notfound(400,"Không tìm thấy order"),
    CATEGORY_HAS_SUBCATEGORIES(400, "Danh mục không thể xóa vì còn danh sách con"),
    CUSTOMER_BAND(400, "Tài khoản đã bị khóa");
    private int code;
     private String message;
     ErrorCode(int code, String message) {
         this.code = code;
         this.message = message;
     }
}
