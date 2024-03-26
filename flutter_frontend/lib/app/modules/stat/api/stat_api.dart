import 'package:intl/intl.dart';

import '../../../core/base/base_remote_source.dart';
import '../../../core/models/debt_model.dart';
import '../../../core/models/stat_model.dart';
import '../../../core/values/api_url_constant.dart';

class StatApi extends BaseRemoteSource {
  Future<StatModel> getDebtCustomerList({
    required String district,
    required String ward,
    required int page,
    required int size,
    required DateTime date,
  }) async {
    final request = dioClient.post(ApiUrlConstants.getDebtCustomerList, data: {
      "provine": "Hà Nội",
      "district": district,
      "ward": ward,
      "page": page,
      "size": size,
      "month": DateFormat('yyyy-MM-dd').format(date),
    });
    try {
      return callApiWithErrorParser(request)
          .then((value) => StatModel.fromJson(value.data));
    } catch (e) {
      rethrow;
    }
  }

  Future<DebtModel> getDebtCustomer({
    required String district,
    required String ward,
    required DateTime date,
  }) async {
    final request = dioClient.post(ApiUrlConstants.getDebtCustomer, data: {
      "provine": "Hà Nội",
      "district": district,
      "ward": ward,
      "month": DateFormat('yyyy-MM-dd').format(date),
    });
    try {
      return callApiWithErrorParser(request)
          .then((value) => DebtModel.fromJson(value.data));
    } catch (e) {
      rethrow;
    }
  }

  Future<StatModel> getNewCustomers({
    required String district,
    required String ward,
    required int page,
    required int size,
    required DateTime date,
  }) async {
    final request = dioClient.post(ApiUrlConstants.getNewCustomers, data: {
      "provine": "Hà Nội",
      "district": district,
      "ward": ward,
      "page": page,
      "size": size,
      "month": DateFormat('yyyy-MM-dd').format(date),
    });
    try {
      return callApiWithErrorParser(request)
          .then((value) => StatModel.fromJson(value.data));
    } catch (e) {
      rethrow;
    }
  }
}
