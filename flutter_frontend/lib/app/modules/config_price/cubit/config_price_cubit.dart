import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../repository/config_price_repository.dart';
import 'config_price_state.dart';

class ConfigPriceCubit extends Cubit<ConfigPriceState> {
  ConfigPriceCubit(this._repo) : super(const ConfigPriceState());

  final ConfigPriceRepository _repo;

  void  getAllPriceList() async {
    final res = _repo.getAllPriceList();
    debugPrint(res.toString());
  }

  void saveAllPriceList() async {
    final res = _repo.saveAllPriceList(List.empty());
    debugPrint(res.toString());
  }
}

