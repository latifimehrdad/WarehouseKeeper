package ir.ngra.warehousekeeper.utility;

public class StaticValues {



    //***** TimeSheet Type *****
    public static int TimeSheetBooth = 0;
    public static int TimeSheetVehicle = 1;
    public static int TimeSheetPackage = 2;
    // ***** TimeSheet Type *****


    //***** Package Request *****
    public static Byte PR_NotRequested = 0;
    public static Byte PR_Requested = 1;
    public static Byte PR_OnProgress = 2;
    public static Byte PR_NoDelivered = 3;
    public static Byte PR_Delivered = 4;
    //***** Package Request *****


    //***** Event Waste Collection State *****
    public static Byte WasteCollectionStateAll = 0;
    public static Byte WasteCollectionStateRequested = 1;
    public static Byte WasteCollectionStateOnProgress = 2;
    public static Byte WasteCollectionStateNoDelivery = 3;
    public static Byte WasteCollectionStateDelivered = 4;
    public static Byte WasteCollectionStateLading = 5;
    public static Byte WasteCollectionStateAcceptLading = 6;
    //***** Event Waste Collection State *****



    //***** Bad Events For Request *****
    public static Byte ML_ResponseError = -125;//درخواست ارسالی ایراد داشته
    public static Byte ML_ResponseFailure = -126;// درخواست انجام نشده به هر دلیلی مثل در دسترس نبودن شبکه
    public static Byte ML_RequestCancel = -127;// شبکه آزاد شده و سرویس لغو شده
    //***** Bad Events For Request *****


    //***** Events Of Collect Request *****
    public static Byte ML_GetItemsOfWasteIsSuccess = 1;
    public static Byte ML_ItemsOFWasteReduce = 0;
    public static Byte ML_ItemsOFWasteAddition = 1;
    //***** Events Of Collect Request *****



    //**** Event Recycling Delivery Type *****
    public static Byte RecyclingDeliveryTypeBooth = 0;
    public static Byte RecyclingDeliveryTypeVehicle = 1;
    //**** Event Recycling Delivery Type *****


    //***** User Massage Type In Call With Us *****
    public static Byte TicketReplyTypeOperator = 1;
    public static Byte TicketReplyTypeRequester = 2;
    //***** User Massage Type In Call With Us *****


    //***** Ticket Status *****
    public static Byte TicketStatusNew = 0;
    public static Byte TicketStatusPending = 1;
    public static Byte TicketStatusWaiting = 2;
    public static Byte TicketStatusAnswered = 3;
    public static Byte TicketStatusReferred = 4;
    public static Byte TicketStatusSolved = 5;
    public static Byte TicketStatusClosed = 9;
    public static Byte TicketStatusArchived = 10;
    //***** Ticket Status *****


    //***** Observable Control *****
    public static Byte ML_GoToHome = 2;// رفتن به صفحه خانه
    public static Byte ML_GotoLogin = 3;// رفتن به صفحه ورد
    public static Byte ML_Success = 4;// یک عملیات با موفقت انجام شده
    public static Byte ML_GotoProfile = 5;// رفتن به صفحه پروفایل
    public static Byte ML_GetProfileInfo = 6;// دریافت اطلاعات از سرور
    public static Byte ML_EditProfile = 7;// ویرایش پروفایل
    public static Byte ML_GetRegion = 8;// در پروفایل دریافت محله
    public static Byte ML_GetCities = 9;// در پروفایل دریافت شهر
    public static Byte ML_GetProvince = 10;// در پروفایل دریافت استان
    public static Byte ML_GetAccountNumbers = 11;// در پروفایل دریافت شماره حساب
    public static Byte ML_GetAccountNumberNull = 12;// در observable در پروفایل دریافت شماره حساب ، شماره حسابی ذخیره نشده باشد
    public static Byte ML_GetBanks = 13;// در observable در پروفایل دریافت لیست بانک ها از سرور
    public static Byte ML_GetRenovationCode = 14;// در observable در پروفایل دریافت کد نوسازی
    public static Byte ML_GetAddress = 15;// در observable دریافت آدرس از روی موقعیت
    public static Byte ML_SetAddress = 16;// در observable آدرس دریافتی تبدیل به یک رشته آدرس شود
    public static Byte ML_GetHousingBuildings = 17;// در observable دریافت نوع ساختمان
    public static Byte ML_GetTimeSheet = 18;// در observable دریافت زمان
    public static Byte ML_SendPackageRequest = 19;// در observable  پکیج ارسال شده
    public static Byte ML_EditUserAddress = 20;// در observable آدرس ویرایش شده
    public static Byte ML_GetBoothList = 21;// در observable دریافت لیست غرفه ها
    public static Byte ML_CollectRequestDone = 22;// در observable دریافت لیست غرفه ها
    public static Byte ML_CollectOrderDone = 22;// در observable دریافت لیست سفارش ها
    public static Byte ML_GetItemLearn = 23; // گرفتن لیست اقلام بازیافتی
    public static Byte ML_GetGiveScore = 24; // گرفتن لیست نحوه امتیاز دهی
    public static Byte ML_GetUserScore = 25; // گرفتن لیست امتیاز کاربر
    public static Byte ML_GetAllDepartments = 26;// گرفتن لیست واحدها برای پشتیبانی
    public static Byte ML_FileDownloaded = 27;
    public static Byte ML_FileDownloading = 28;
    public static Byte ML_GoToUpdate = 29;// نسخه جدید برنامه
    public static Byte ML_DialogClose = 30;
    public static Byte ML_GetAllTicket = 31;
    public static Byte ML_GetAllTicketReply = 32;
    public static Byte ML_CloseTicket = 33;
    public static Byte ML_GetVolume = 34;
    public static Byte ML_GetUserAddress = 35;
    public static Byte ML_GetBestScore = 36;
    public static Byte ML_GetWasteCollection = 37;
    public static Byte ML_GotoVerify = 38;
    //***** Observable Control *****


}
