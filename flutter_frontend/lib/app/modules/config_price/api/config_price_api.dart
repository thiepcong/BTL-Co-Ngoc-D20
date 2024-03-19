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

  Future<String> saveAllPriceList({required List<PriceList> data}) async {
    final request = dioClient.post(ApiUrlConstants.configPrice, data: {
      'data': data.map((e) => e.toJson()).toList(),
    });
    try {
      return callApiWithErrorParser(request)
          .then((value) => value.data['message']);
    } catch (e) {
      rethrow;
    }
  }
}
