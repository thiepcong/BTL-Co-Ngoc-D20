import '../../../core/base/base_remote_source.dart';
import '../../../core/values/api_url_constant.dart';

class LoginApi extends BaseRemoteSource {
  Future<String> login({
    required String usename,
    required String password,
  }) async {
    final request = dioClient.post(ApiUrlConstants.login, data: {
      "username": usename,
      "password": password,
    });
    try {
      return callApiWithErrorParser(request)
          .then((value) => value.data["token"]);
    } catch (e) {
      rethrow;
    }
  }
}
