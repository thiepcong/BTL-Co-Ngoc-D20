import 'package:dio/dio.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../core/values/secure_key_constant.dart';

class RequestHeaderInterceptor extends InterceptorsWrapper {
  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) {
    getCustomHeaders().then((customHeaders) {
      options.headers.addAll(customHeaders);
      super.onRequest(options, handler);
    });
  }

  Future<Map<String, String>> getCustomHeaders() async {
    final pre = await SharedPreferences.getInstance();
    final accessToken = pre.getString(SecureKeyConstants.accessToken);
    var customHeaders = {
      'content-type': 'application/json',
      'Accept': "application/json"
    };
    if (accessToken != null && accessToken.isNotEmpty) {
      customHeaders.addAll({
        'Authorization': "Bearer $accessToken",
      });
    }

    return customHeaders;
  }
}
