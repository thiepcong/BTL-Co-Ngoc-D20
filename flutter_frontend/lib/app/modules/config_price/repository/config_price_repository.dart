import '../../../core/models/price_list.dart';
import '../api/config_price_api.dart';

class ConfigPriceRepository {
  final ConfigPriceApi _api;

  ConfigPriceRepository(this._api);

  Future<List<PriceList>> getAllPriceList() async {
    return _api.getAllPriceList();
  }

  Future<int> savePriceList(PriceList data) async {
    return _api.savePriceList(data: data);
  }
}
