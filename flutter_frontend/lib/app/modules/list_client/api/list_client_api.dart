import 'package:intl/intl.dart';

import '../../../core/base/base_remote_source.dart';
import '../../../core/models/stat_model.dart';
import '../../../core/values/api_url_constant.dart';

class ListClientApi extends BaseRemoteSource {
  Future<StatModel> getListClient({
    required String district,
    required String ward,
    required int page,
    required int size,
    DateTime? date,
    bool? type,
  }) async {
    final req = {
      "provine": "Hà Nội",
      "district": district,
      "ward": ward,
      "page": page,
      "size": size,
    };
    if (date != null) {
      req.addAll({"month": DateFormat('yyyy-MM-dd').format(date)});
    }
    final request = dioClient.post(
        type == null
            ? ApiUrlConstants.listClient
            : type == true
                ? ApiUrlConstants.getPaidCustomers
                : ApiUrlConstants.unPaidListByAdsress,
        data: req);
    try {
      return callApiWithErrorParser(request)
          .then((value) => StatModel.fromJson(value.data));
    } catch (e) {
      rethrow;
    }
  }
}
