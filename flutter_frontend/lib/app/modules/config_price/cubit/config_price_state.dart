import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/price_list.dart';
import '../../../core/models/price_scale.dart';

part 'config_price_state.freezed.dart';

@freezed
class ConfigPriceState with _$ConfigPriceState {
  const ConfigPriceState._();

  const factory ConfigPriceState({
    @Default(false) bool isLoading,
    @Default(false) bool saveDone,
    @Default([]) List<PriceList> all,
    PriceList? currentItem,
    @Default([]) List<PriceScale> currentList,
    PriceScale? lastPriceScale,
    bool? isValidate,
    String? message,
    Error? error,
  }) = _ConfigPriceState;
}
