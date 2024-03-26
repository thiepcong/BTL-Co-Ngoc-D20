import '../../../core/base/base_remote_source.dart';
import '../../../core/models/login_response.dart';
import '../../../core/values/api_url_constant.dart';

class LoginApi extends BaseRemoteSource {
  Future<LoginResponse> login({
    required String usename,
    required String password,
  }) async {
    final request = dioClient.post(ApiUrlConstants.login, data: {
      "username": usename,
      "password": password,
    });
    try {
      return callApiWithErrorParser(request)
          .then((value) => LoginResponse.fromJson(value.data));
    } catch (e) {
      rethrow;
    }
  }
}
