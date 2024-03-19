import 'package:auto_route/auto_route.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/main_router.dart';

import '../../../core/values/text_styles.dart';
import '../cubit/login_cubit.dart';
import '../cubit/login_state.dart';
import '../repository/login_repository.dart';

class LoginView extends StatefulWidget {
  const LoginView({super.key});

  @override
  State<LoginView> createState() => _LoginViewState();
}

class _LoginViewState extends State<LoginView> {
  final TextEditingController _usernameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) => LoginCubit(context.read<LoginRepository>()),
      child: _buildPage(context),
    );
  }

  Widget _buildPage(BuildContext context) {
    return BlocBuilder<LoginCubit, LoginState>(
      builder: (context, state) {
        final cubit = context.read<LoginCubit>();
        return Scaffold(
          appBar: AppBar(
            automaticallyImplyLeading: false,
            title: const Text('Login'),
          ),
          body: Center(
            child: Container(
              decoration: BoxDecoration(
                border: Border.all(),
                borderRadius: const BorderRadius.all(Radius.circular(12)),
              ),
              padding: const EdgeInsets.symmetric(horizontal: 24),
              width: 400,
              height: 500,
              child: Form(
                key: _formKey,
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                      "Login",
                      style: TextStyles.boldBlackS28,
                    ),
                    TextFormField(
                      controller: _usernameController,
                      decoration: const InputDecoration(
                        labelText: 'Username',
                        border: OutlineInputBorder(),
                      ),
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Please enter your username';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),
                    TextFormField(
                      controller: _passwordController,
                      obscureText: true,
                      decoration: const InputDecoration(
                        labelText: 'Password',
                        border: OutlineInputBorder(),
                      ),
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Please enter your password';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),
                    ElevatedButton(
                      onPressed: () {
                        if (_formKey.currentState!.validate()) {
                          // cubit.login(_usernameController.text, _passwordController.text);
                          context.pushRoute(const ManagerViewRoute());
                          cubit;
                        }
                      },
                      child: const Text('Login'),
                    ),
                  ],
                ),
              ),
            ),
          ),
        );
      },
    );
  }
}
