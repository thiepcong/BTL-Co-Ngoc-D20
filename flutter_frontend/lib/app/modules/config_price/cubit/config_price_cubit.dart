import 'dart:developer';

import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/models/price_list.dart';
import '../../../core/models/price_scale.dart';
import '../repository/config_price_repository.dart';
import 'config_price_state.dart';

class ConfigPriceCubit extends Cubit<ConfigPriceState> {
  ConfigPriceCubit(this._repo) : super(const ConfigPriceState());

  final ConfigPriceRepository _repo;

  void init() {
    emit(state.copyWith(
        curentItem: PriceList(
            id: 1,
            applyDate: DateTime.now(),
            description: 'description',
            type: 'type',
            items: [])));
  }

  bool checkPriceListValidate() {
    try {
      List<PriceScale> li = List.from(state.curentItem?.items ?? []);
      if (li.isEmpty) return false;
      for (int i = 0; i < li.length; i++) {
        if (li[i].startIndex >= li[i].endIndex) return false;
        if (i + 1 < li.length && li[i].startIndex > li[i + 1].startIndex) {
          return false;
        }
      }
      return true;
    } catch (e) {
      log(e.toString());
      rethrow;
    }
  }

  void save() {
    final validate = checkPriceListValidate();
    emit(state.copyWith(isValidate: validate));
    if (validate) {
      //
    }
  }

  void getAllPriceList() async {
    final res = _repo.getAllPriceList();
    debugPrint(res.toString());
  }

  void saveAllPriceList() async {
    final res = _repo.saveAllPriceList(List.empty());
    debugPrint(res.toString());
  }

  void addPriceScale() {
    List<PriceScale> li = List.from(state.curentItem?.items ?? []);
    li.add(PriceScale(
      startIndex: li.isEmpty ? 0 : li[li.length - 1].endIndex,
      endIndex: 0,
      price: 0,
    ));
    emit(state.copyWith(curentItem: state.curentItem?.copyWith(items: li)));
  }

  void deletePriceScaleByIndex(int index) {
    List<PriceScale> li = List.from(state.curentItem?.items ?? []);
    li.removeAt(index);
    emit(state.copyWith(curentItem: state.curentItem?.copyWith(items: li)));
  }

  void updatePriceListByItem(
    int index, {
    String? startIndex,
    String? endIndex,
    String? price,
  }) {
    final newStartIndex = int.tryParse(startIndex ?? '');
    final newEndIndex = int.tryParse(endIndex ?? '');
    final newPrice = int.tryParse(price ?? '');
    List<PriceScale> li = List.from(state.curentItem?.items ?? []);
    li[index] = li[index].copyWith(
      startIndex: newStartIndex,
      endIndex: newEndIndex,
      price: newPrice,
    );
    if (index + 1 < li.length) {
      li[index + 1] = li[index + 1].copyWith(startIndex: newEndIndex);
    }
    emit(state.copyWith(
      curentItem: state.curentItem?.copyWith(items: li),
      isValidate: null,
    ));
  }
}
