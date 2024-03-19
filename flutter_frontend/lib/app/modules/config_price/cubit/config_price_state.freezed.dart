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
  List<PriceList> get current => throw _privateConstructorUsedError;
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
      List<PriceList> current,
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
    Object? current = null,
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_value.copyWith(
      all: null == all
          ? _value.all
          : all // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
      current: null == current
          ? _value.current
          : current // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
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
      List<PriceList> current,
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
    Object? current = null,
    Object? message = freezed,
    Object? error = freezed,
  }) {
    return _then(_$ConfigPriceStateImpl(
      all: null == all
          ? _value._all
          : all // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
      current: null == current
          ? _value._current
          : current // ignore: cast_nullable_to_non_nullable
              as List<PriceList>,
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
      final List<PriceList> current = const [],
      this.message,
      this.error})
      : _all = all,
        _current = current,
        super._();

  final List<PriceList> _all;
  @override
  @JsonKey()
  List<PriceList> get all {
    if (_all is EqualUnmodifiableListView) return _all;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_all);
  }

  final List<PriceList> _current;
  @override
  @JsonKey()
  List<PriceList> get current {
    if (_current is EqualUnmodifiableListView) return _current;
    // ignore: implicit_dynamic_type
    return EqualUnmodifiableListView(_current);
  }

  @override
  final String? message;
  @override
  final Error? error;

  @override
  String toString() {
    return 'ConfigPriceState(all: $all, current: $current, message: $message, error: $error)';
  }

  @override
  bool operator ==(Object other) {
    return identical(this, other) ||
        (other.runtimeType == runtimeType &&
            other is _$ConfigPriceStateImpl &&
            const DeepCollectionEquality().equals(other._all, _all) &&
            const DeepCollectionEquality().equals(other._current, _current) &&
            (identical(other.message, message) || other.message == message) &&
            (identical(other.error, error) || other.error == error));
  }

  @override
  int get hashCode => Object.hash(
      runtimeType,
      const DeepCollectionEquality().hash(_all),
      const DeepCollectionEquality().hash(_current),
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
      final List<PriceList> current,
      final String? message,
      final Error? error}) = _$ConfigPriceStateImpl;
  const _ConfigPriceState._() : super._();

  @override
  List<PriceList> get all;
  @override
  List<PriceList> get current;
  @override
  String? get message;
  @override
  Error? get error;
  @override
  @JsonKey(ignore: true)
  _$$ConfigPriceStateImplCopyWith<_$ConfigPriceStateImpl> get copyWith =>
      throw _privateConstructorUsedError;
}
