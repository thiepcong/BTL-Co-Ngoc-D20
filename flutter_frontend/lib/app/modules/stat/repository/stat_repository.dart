import '../../../core/models/debt_model.dart';
import '../../../core/models/new_customer.dart';
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

  Future<NewCustomer> getNewCustomers({
    String? district,
    String? ward,
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

  Future<StatModel> getNewCustomersList({
    String? district,
    String? ward,
    required int page,
    required int size,
    required DateTime date,
  }) async {
    return _api.getNewCustomersList(
      district: district,
      ward: ward,
      page: page,
      size: size,
      date: date,
    );
  }

  Future<StatModel> getRevenueList({
    String? district,
    String? ward,
    required int page,
    required int size,
    required DateTime date,
  }) {
    return _api.getRevenueList(
      district: district,
      ward: ward,
      page: page,
      size: size,
      date: date,
    );
  }
}
