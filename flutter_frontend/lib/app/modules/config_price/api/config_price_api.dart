import '../../../core/base/base_remote_source.dart';
import '../../../core/models/price_list.dart';
import '../../../core/values/api_url_constant.dart';

class ConfigPriceApi extends BaseRemoteSource {
  Future<List<PriceList>> getAllPriceList() async {
    final request = dioClient.get(ApiUrlConstants.configPrice);
    try {
      return callApiWithErrorParser(request).then((value) =>
          (value.data['data'] as List)
              .map((e) => PriceList.fromJson(e))
              .toList());
    } catch (e) {
      rethrow;
    }
  }

  Future<int> savePriceList({required PriceList data}) async {
    final request = dioClient.post(ApiUrlConstants.configPrice2(data.userType.id), data:data.toJson());
    try {
      return callApiWithErrorParser(request)
          .then((value) => value.data['id']);
    } catch (e) {
      rethrow;
    }
  }
}
