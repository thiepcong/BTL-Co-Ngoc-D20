// coverage:ignore-file
// GENERATED CODE - DO NOT MODIFY BY HAND
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'config_price_state.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

T _$identity<T>(T value) => value;

final _privateConstructorUsedError = UnsupportedError(
    'It seems like you constructed your class using `MyClass._()`. This constructor is only meant to be used by freezed and you are not supposed to need it nor use it.\nPlease check the documentation here for more information: https://github.com/rrousselGit/freezed#adding-getters-and-methods-to-our-models');

/// @nodoc
mixin _$ConfigPriceState {
  List<PriceList> get all => throw _privateConstructorUsedError;
  List<PriceList> get currentList => throw _privateConstructorUsedError;
  PriceList? get lastPriceScale => throw _privateConstructorUsedError;
  bool? get isValidate => throw _privateConstructorUsedError;
  PriceList? get curentItem => throw _privateConstructorUsedError;
  String? get message => throw _privateConstructorUsedError;
  Error? get error => throw _privateConstructorUsedError;

  @JsonKey(ignore: true)
  $ConfigPriceStateCopyWith<ConfigPriceState> get copyWith =>
      throw _privateConstructorUsedError;
}

/// @nodoc
abstract class $ConfigPriceStateCopyWith<$Res> {
  factory $ConfigPriceStateCopyWith(
          ConfigPriceState value, $Res Function(ConfigPriceState) then) =
      _$ConfigPriceStateCopyWithImpl<$Res, ConfigPriceState>;
  @useResult
  $Res call(
      {List<PriceList> all,
      List<PriceList> currentList,
      PriceList? lastPriceScale,
      bool? isValidate,
      PriceList? curentItem,
      String? message,
      Error? error});
}

/// @nodoc
class _$ConfigPriceStateCopyWithImpl<$Res, $Val extends ConfigPriceState>
    implements $ConfigPriceStateCopyWith<$Res> {
  _$ConfigPriceStateCopyWithImpl(this._value, this._then);

  // ignore: unused_field
  final $Val _value;
  // ignore: unused_field
  final $Res Function($Val) _then;

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? all = null,
    Object? currentList = null,
    Object? lastPriceScale = freezed,
    Object? isValidate = freezed,
    Object? curentItem = freezed,
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_value.copyWith(
      all: null == all
          ? _value.all
          : all // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
      currentList: null == currentList
          ? _value.currentList
          : currentList // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
      lastPriceScale: freezed == lastPriceScale
          ? _value.lastPriceScale
          : lastPriceScale // ignore: cast_nullable_to_non_nullable
              as PriceList?,
      isValidate: freezed == isValidate
          ? _value.isValidate
          : isValidate // ignore: cast_nullable_to_non_nullable
              as bool?,
      curentItem: freezed == curentItem
          ? _value.curentItem
          : curentItem // ignore: cast_nullable_to_non_nullable
              as PriceList?,
      message: freezed == message
          ? _value.message
          : message // ignore: cast_nullable_to_non_nullable
              as String?,
      error: freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as Error?,
    ) as $Val);
  }
}

/// @nodoc
abstract class _$$ConfigPriceStateImplCopyWith<$Res>
    implements $ConfigPriceStateCopyWith<$Res> {
  factory _$$ConfigPriceStateImplCopyWith(_$ConfigPriceStateImpl value,
          $Res Function(_$ConfigPriceStateImpl) then) =
      __$$ConfigPriceStateImplCopyWithImpl<$Res>;
  @override
  @useResult
  $Res call(
      {List<PriceList> all,
      List<PriceList> currentList,
      PriceList? lastPriceScale,
      bool? isValidate,
      PriceList? curentItem,
      String? message,
      Error? error});
}

/// @nodoc
class __$$ConfigPriceStateImplCopyWithImpl<$Res>
    extends _$ConfigPriceStateCopyWithImpl<$Res, _$ConfigPriceStateImpl>
    implements _$$ConfigPriceStateImplCopyWith<$Res> {
  __$$ConfigPriceStateImplCopyWithImpl(_$ConfigPriceStateImpl _value,
      $Res Function(_$ConfigPriceStateImpl) _then)
      : super(_value, _then);

  @pragma('vm:prefer-inline')
  @override
  $Res call({
    Object? all = null,
    Object? currentList = null,
    Object? lastPriceScale = freezed,
    Object? isValidate = freezed,
    Object? curentItem = freezed,
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_$ConfigPriceStateImpl(
      all: null == all
          ? _value._all
          : all // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
      currentList: null == currentList
          ? _value._currentList
          : currentList // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
      lastPriceScale: freezed == lastPriceScale
          ? _value.lastPriceScale
          : lastPriceScale // ignore: cast_nullable_to_non_nullable
              as PriceList?,
      isValidate: freezed == isValidate
          ? _value.isValidate
          : isValidate // ignore: cast_nullable_to_non_nullable
              as bool?,
      curentItem: freezed == curentItem
          ? _value.curentItem
          : curentItem // ignore: cast_nullable_to_non_nullable
              as PriceList?,
      message: freezed == message
          ? _value.message
          : message // ignore: cast_nullable_to_non_nullable
              as String?,
      error: freezed == error
          ? _value.error
          : error // ignore: cast_nullable_to_non_nullable
              as Error?,
    ));
  }
}

/// @nodoc

class _$ConfigPriceStateImpl extends _ConfigPriceState {
  const _$ConfigPriceStateImpl(
      {final List<PriceList> all = const [],
      final List<PriceList> currentList = const [],
      this.lastPriceScale,
      this.isValidate,
      this.curentItem,
      this.message,
      this.error})
      : _all = all,
        _currentList = currentList,
        super._();

  final List<PriceList> _all;
  @override
  @JsonKey()
  List<PriceList> get all {
    if (_all is EqualUnmodifiableListView) return _all;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_all);
  }

  final List<PriceList> _currentList;
  @override
  @JsonKey()
  List<PriceList> get currentList {
    if (_currentList is EqualUnmodifiableListView) return _currentList;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_currentList);
  }

  @override
  final PriceList? lastPriceScale;
  @override
  final bool? isValidate;
  @override
  final PriceList? curentItem;
  @override
  final String? message;
  @override
  final Error? error;

  @override
  String toString() {
    return 'ConfigPriceState(all: $all, currentList: $currentList, lastPriceScale: $lastPriceScale, isValidate: $isValidate, curentItem: $curentItem, message: $message, error: $error)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ConfigPriceStateImpl &&
            const DeepCollectionEquality().equals(other._all, _all) &&
            const DeepCollectionEquality()
                .equals(other._currentList, _currentList) &&
            (identical(other.lastPriceScale, lastPriceScale) ||
                other.lastPriceScale == lastPriceScale) &&
            (identical(other.isValidate, isValidate) ||
                other.isValidate == isValidate) &&
            (identical(other.curentItem, curentItem) ||
                other.curentItem == curentItem) &&
            (identical(other.message, message) || other.message == message) &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_all),
      const DeepCollectionEquality().hash(_currentList),
      lastPriceScale,
      isValidate,
      curentItem,
      message,
      error);

  @JsonKey(ignore: true)
  @override
  @pragma('vm:prefer-inline')
  _$$ConfigPriceStateImplCopyWith<_$ConfigPriceStateImpl> get copyWith =>
      __$$ConfigPriceStateImplCopyWithImpl<_$ConfigPriceStateImpl>(
          this, _$identity);
}

abstract class _ConfigPriceState extends ConfigPriceState {
  const factory _ConfigPriceState(
      {final List<PriceList> all,
      final List<PriceList> currentList,
      final PriceList? lastPriceScale,
      final bool? isValidate,
      final PriceList? curentItem,
      final String? message,
      final Error? error}) = _$ConfigPriceStateImpl;
  const _ConfigPriceState._() : super._();

  @override
  List<PriceList> get all;
  @override
  List<PriceList> get currentList;
  @override
  PriceList? get lastPriceScale;
  @override
  bool? get isValidate;
  @override
  PriceList? get curentItem;
  @override
  String? get message;
  @override
  Error? get error;
  @override
  @JsonKey(ignore: true)
  _$$ConfigPriceStateImplCopyWith<_$ConfigPriceStateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
