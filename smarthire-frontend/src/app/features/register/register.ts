import { Component } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  registerForm;

  errorMessage = '';
  successMessage = '';
  loading = false;

  private apiUrl =
    'http://localhost:8080/api/auth/register';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {

    this.registerForm = this.fb.group({

      name: [
        '',
        Validators.required
      ],

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
      ],

      confirmPassword: [
        '',
        Validators.required
      ]

    });
  }


  onRegister(): void {

    console.log('CREATE ACCOUNT BUTTON CLICKED');

    this.errorMessage = '';
    this.successMessage = '';

    /* Check form validation */

    if (this.registerForm.invalid) {

      this.registerForm.markAllAsTouched();

      return;
    }


    /* Get form values */

    const formValue =
      this.registerForm.value;


    /* Check passwords */

    if (
      formValue.password !==
      formValue.confirmPassword
    ) {

      this.errorMessage =
        'Passwords do not match.';

      return;
    }


    /* Prepare request */

    const registerData = {

      name: formValue.name,

      email: formValue.email,

      password: formValue.password

    };


    console.log(
      'Sending registration data:',
      registerData
    );


    this.loading = true;


    /* Send data to Spring Boot */

    this.http
      .post(
        this.apiUrl,
        registerData
      )
      .subscribe({

        next: (response) => {

          console.log(
            'Registration successful:',
            response
          );

          this.loading = false;

          this.successMessage =
            'Account created successfully! Redirecting to login...';


          /* Go to login page */

          setTimeout(() => {

            this.router.navigate([
              '/login'
            ]);

          }, 1200);

        },


        error: (error) => {

          console.error(
            'Registration failed:',
            error
          );

          this.loading = false;


          if (
            error.status === 409
          ) {

            this.errorMessage =
              'An account with this email already exists.';

          }

          else if (
            error.status === 400
          ) {

            this.errorMessage =
              'Please check your registration details.';

          }

          else if (
            error.status === 0
          ) {

            this.errorMessage =
              'Cannot connect to the server. Make sure Spring Boot is running.';

          }

          else {

            this.errorMessage =
              'Registration failed. Please try again.';

          }

        }

      });
  }
}