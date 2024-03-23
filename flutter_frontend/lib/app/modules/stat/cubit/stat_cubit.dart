import 'package:flutter_bloc/flutter_bloc.dart';
import 'stat_state.dart';

class StatCubit extends Cubit<StatState> {
  StatCubit() : super(const StatState());

  void setCurrentDistrict(String value) {
    emit(state.copyWith(currentDistrict: value, currentWard: null));
  }

  void setCurrentWard(String value) {
    emit(state.copyWith(currentWard: value));
  }
}
