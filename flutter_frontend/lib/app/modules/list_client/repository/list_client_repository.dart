import '../../../core/models/stat_model.dart';
import '../api/list_client_api.dart';

class ListClientRepository {
  final ListClientApi _api;
  ListClientRepository(this._api);

  Future<StatModel> getListClient({
    String? district,
    String? ward,
    required int page,
    required int size,
    String? key,
    DateTime? date,
    bool? type,
  }) async {
    return _api.getListClient(
      district: district,
      ward: ward,
      key: key,
      page: page,
      size: size,
      date: date,
      type: type,
    );
  }
}
