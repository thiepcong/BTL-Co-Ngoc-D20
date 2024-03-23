import '../../../core/base/base_remote_source.dart';
import '../../../core/values/api_url_constant.dart';

class TranferMailApi extends BaseRemoteSource {
  Future<String> getAllTemplate({
    required int pageNum,required int pageSize,
  }) async {
    final request = dioClient.get(ApiUrlConstants.tranferMail(pageNum,pageSize));
    try {
      return callApiWithErrorParser(request)
          .then((value) => value.data["token"]);
    } catch (e) {
      rethrow;
    }
  }
}
