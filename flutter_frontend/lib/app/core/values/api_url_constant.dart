import '../../network/dio_provider.dart';

final baseUrl = DioProvider.baseUrl;

class ApiUrlConstants {
  // static String register = '$baseUrl/api/register';

  static String login = '$baseUrl/api/login';

  static String configPrice = '$baseUrl/api/config_price';

  static String configPrice2(int x) => '$baseUrl/api/config_price/$x';

  static String tranferMail(int pageNum, int pageSize) =>
      '$baseUrl/api/emailTemplate?pageNum=$pageNum&pageSize=$pageSize';
}
