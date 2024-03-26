import '../../../core/models/debt_model.dart';
import '../../../core/models/stat_model.dart';
import '../api/stat_api.dart';

class StatRepository {
  final StatApi _api;

  StatRepository(this._api);

  Future<StatModel> getDebtCustomerList({
    required String district,
    required String ward,
    required int page,
    required int size,
    required DateTime date,
  }) async {
    return _api.getDebtCustomerList(
      district: district,
      ward: ward,
      page: page,
      size: size,
      date: date,
    );
  }

  Future<DebtModel> getDebtCustomer({
    required String district,
    required String ward,
    required DateTime date,
  }) async {
    return _api.getDebtCustomer(
      district: district,
      ward: ward,
      date: date,
    );
  }

  Future<StatModel> getNewCustomers({
    required String district,
    required String ward,
    required int page,
    required int size,
    required DateTime date,
  }) async {
    return _api.getNewCustomers(
      district: district,
      ward: ward,
      page: page,
      size: size,
      date: date,
    );
  }
}
