import 'dart:developer';

import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/models/price_list.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import 'package:flutter_frontend/app/network/exceptions/network_exception.dart';
import '../../../core/models/price_scale.dart';
import '../repository/config_price_repository.dart';
import 'config_price_state.dart';

class ConfigPriceCubit extends Cubit<ConfigPriceState> {
  ConfigPriceCubit(this._repo) : super(const ConfigPriceState());

  final ConfigPriceRepository _repo;

  void init() {
    getAllPriceList();
  }

  bool checkPriceListValidate() {
    try {
      List<PriceScale> li = List.from(state.currentList);
      if (li.isEmpty) return false;
      for (int i = 0; i < li.length; i++) {
        if (li[i].startIndex >= li[i].endIndex) return false;
        if (i + 1 < li.length && li[i].startIndex > li[i + 1].startIndex) {
          return false;
        }
      }
      final last = li.last;
      if (state.lastPriceScale != null) {
        if (last.startIndex > state.lastPriceScale!.startIndex) return false;
      }
      return true;
    } catch (e) {
      log(e.toString());
      rethrow;
    }
  }

  void save(DateTime e) {
    final validate = checkPriceListValidate();
    emit(state.copyWith(isValidate: validate));
    if (validate) {
      final req = state.currentItem?.copyWith(
          applyDate: e,
          listPriceScales: [...state.currentList, state.lastPriceScale!]);
      if (req != null) savePriceList(req);
    }
  }

  void getAllPriceList() async {
    emit(state.copyWith(isLoading: true));
    final res = await _repo.getAllPriceList();
    final re = handleInit(res, 'Hộ Nghèo');
    if (re != null) {
      emit(state.copyWith(
        currentList:
            re.listPriceScales.sublist(0, re.listPriceScales.length - 1),
        lastPriceScale: re.listPriceScales.last.copyWith(endIndex: 100000),
        currentItem: re,
      ));
    }
    emit(state.copyWith(all: res, isLoading: false));
  }

  void getByType(String type) {
    emit(state.copyWith(isLoading: true));
    final res = state.all;
    final re = handleInit(res, type);
    if (re != null) {
      emit(state.copyWith(
        currentList:
            re.listPriceScales.sublist(0, re.listPriceScales.length - 1),
        lastPriceScale: re.listPriceScales.last.copyWith(endIndex: 100000),
        currentItem: re,
      ));
    }
    emit(state.copyWith(all: res, isLoading: false));
  }

  PriceList? handleInit(List<PriceList> li, String type) {
    for (final i in li) {
      if (i.userType.typeName == type) return i;
    }
    return null;
  }

  void savePriceList(PriceList priceList) async {
    try {
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.savePriceList(priceList);
      List<PriceList> all = List.from(state.all);
      all.insert(0, priceList);
      emit(state.copyWith(
        isLoading: false,
        message: "Lưu Thành Công",
        all: all,
      ));
      debugPrint(res.toString());
    } catch (e) {
      if (e is BadRequestException || e is NetworkException) {
        emit(state.copyWith(
            isLoading: false,
            message:
                e is BadRequestException ? e.message : "Vui lòng thử lại sau"));
        return;
      }
      rethrow;
    }
  }

  void addPriceScale() {
    List<PriceScale> li = List.from(state.currentList);
    li.add(PriceScale(
      startIndex: li.isEmpty ? 0 : li[li.length - 1].endIndex,
      endIndex: 0,
      price: 0,
    ));
    emit(state.copyWith(currentList: li));
  }

  void deletePriceScaleByIndex(int index) {
    List<PriceScale> li = List.from(state.currentList);
    final deleteItem = li.removeAt(index);
    if (li.isNotEmpty) {
      li[0] = li[0].copyWith(startIndex: 0);
      if (index - 1 >= 0) {
        li[index - 1] = li[index - 1].copyWith(endIndex: deleteItem.endIndex);
      }
    } else {
      emit(state.copyWith(
          lastPriceScale: state.lastPriceScale?.copyWith(startIndex: 0)));
    }
    emit(state.copyWith(currentList: li));
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
    List<PriceScale> li = List.from(state.currentList);
    li[index] = li[index].copyWith(
      startIndex: newStartIndex,
      endIndex: newEndIndex,
      price: newPrice,
    );
    if (index + 1 < li.length) {
      li[index + 1] = li[index + 1].copyWith(startIndex: newEndIndex);
    } else if (index == li.length - 1) {
      emit(state.copyWith(
        lastPriceScale: state.lastPriceScale?.copyWith(startIndex: newEndIndex),
      ));
    }
    emit(state.copyWith(
      currentList: li,
      isValidate: null,
    ));
  }
}
