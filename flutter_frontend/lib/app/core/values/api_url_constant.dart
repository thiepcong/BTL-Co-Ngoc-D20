import '../../network/dio_provider.dart';

final baseUrl = DioProvider.baseUrl;

class ApiUrlConstants {
  // static String register = '$baseUrl/api/register';

  static String login = '$baseUrl/api/login';

  static String configPrice = '$baseUrl/api/config_price';
}
