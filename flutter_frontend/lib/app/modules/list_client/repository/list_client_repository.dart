import '../../../core/models/stat_model.dart';
import '../api/list_client_api.dart';

class ListClientRepository {
  final ListClientApi _api;
  ListClientRepository(this._api);

  Future<StatModel> getListClient({
    required String district,
    required String ward,
    required int page,
    required int size,
    DateTime? date,
    bool? type,
  }) async {
    return _api.getListClient(
      district: district,
      ward: ward,
      page: page,
      size: size,
      date: date,
      type: type,
    );
  }
}
