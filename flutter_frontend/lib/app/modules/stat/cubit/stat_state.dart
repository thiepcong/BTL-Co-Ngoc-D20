import 'package:freezed_annotation/freezed_annotation.dart';

import '../../../core/models/customer.dart';
import '../../../core/models/debt_model.dart';
import '../../../core/models/stat_model.dart';

part 'stat_state.freezed.dart';

@freezed
class StatState with _$StatState {
  const StatState._();

  const factory StatState({
    @Default(false) bool isLoading,
    @Default([]) List<Customer> customerMails,
    StatModel? currentItem,
    DebtModel? currentDebt,
    String? currentDistrict,
    String? currentWard,
    DateTime? currentSelectDate,
    String? message,
    Error? error,
  }) = _StatState;
}
