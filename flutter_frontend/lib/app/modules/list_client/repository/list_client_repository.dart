import '../../../core/models/customer.dart';
import '../api/list_client_api.dart';

class ListClientRepository {
  final ListClientApi _api;
  ListClientRepository(this._api);

  Future<List<Customer>> getListClient({
    required String district,
    required String ward,
    required int page,
    required int size,
  }) async {
    return _api.getListClient(
        district: district, ward: ward, page: page, size: size);
  }
}
