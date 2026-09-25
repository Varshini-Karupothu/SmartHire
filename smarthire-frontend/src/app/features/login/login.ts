import { Component } from '@angular/core';
import {
FormBuilder,
ReactiveFormsModule,
Validators
} from '@angular/forms';

import {
Router,
RouterLink
} from '@angular/router';

import { AuthService } from '../../core/services/auth.service';

@Component({
selector: 'app-login',
imports: [
ReactiveFormsModule,
RouterLink
],
templateUrl: './login.html',
styleUrl: './login.css'
})
export class Login {

loginForm;

errorMessage = '';
isLoading = false;

constructor(
private fb: FormBuilder,
private authService: AuthService,
private router: Router
) {

this.loginForm = this.fb.group({

  email: [
    '',
    [
      Validators.required,
      Validators.email
    ]
  ],

  password: [
    '',
    [
      Validators.required,
      Validators.minLength(6)
    ]
  ]

});


}

onLogin(): void {

if (this.loginForm.invalid) {

  this.loginForm.markAllAsTouched();

  return;
}

this.errorMessage = '';
this.isLoading = true;

const email =
  this.loginForm.value.email ?? '';

const password =
  this.loginForm.value.password ?? '';

this.authService
  .login({
    email: email,
    password: password
  })
  .subscribe({

    next: () => {

      this.isLoading = false;

      console.log(
        'Login successful'
      );

      this.router.navigate([
        '/dashboard'
      ]);

    },

    error: (error) => {

      this.isLoading = false;

      console.error(
        'Login failed:',
        error
      );

      this.errorMessage =
        'Invalid email or password.';

    }

  });


}
}
