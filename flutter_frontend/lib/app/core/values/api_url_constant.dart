import '../../network/dio_provider.dart';

final baseUrl = DioProvider.baseUrl;

class ApiUrlConstants {
  // static String register = '$baseUrl/api/register';

  static String login = '$baseUrl/api/auth/login';

  static String configPrice = '$baseUrl/api/price_list';

  static String configPrice2(int x) => '$baseUrl/api/price_list/$x';

  static String tranferMail(int pageNum, int pageSize) =>
      '$baseUrl/api/emailTemplate?pageNum=$pageNum&pageSize=$pageSize';

  static String listClient = '$baseUrl/api/customer/listByAdsress';

  static String getPaidCustomers = '$baseUrl/api/customer/getPaidCustomers';

  static String unPaidListByAdsress = '$baseUrl/api/customer/UnPaidListByAdsress';

  static String getDebtCustomer = '$baseUrl/api/report/getDebtCustomer';

  static String getDebtCustomerList = '$baseUrl/api/report/getDebtCustomerList';

  static String getNewCustomers = '$baseUrl/api/report/getNewCustomers';
}
