import {
  Component,
  ChangeDetectorRef
} from '@angular/core';

import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

import { RouterLink } from '@angular/router';

import {
  ApplicationService,
  JobApplication
} from '../../services/application';

@Component({
  selector: 'app-applications',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './applications.html',
  styleUrl: './applications.css'
})
export class Applications {

  applicationForm;

  applications: JobApplication[] = [];

  isLoading = false;

  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private applicationService: ApplicationService,
    private cdr: ChangeDetectorRef
  ) {

    this.applicationForm = this.fb.group({

      company: [
        '',
        Validators.required
      ],

      role: [
        '',
        Validators.required
      ],

      date: [
        '',
        Validators.required
      ],

      status: [
        'Applied',
        Validators.required
      ]

    });

    this.loadApplications();
  }

  loadApplications(): void {

    this.isLoading = true;

    this.errorMessage = '';

    this.applicationService
      .getApplications()
      .subscribe({

        next: (applications) => {

          console.log(
            'Applications loaded:',
            applications
          );

          this.applications = applications;

          this.isLoading = false;

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to load applications:',
            error
          );

          this.errorMessage =
            'Failed to load applications. Please try again.';

          this.isLoading = false;

          this.cdr.detectChanges();

        }

      });
  }

  addApplication(): void {

    if (this.applicationForm.invalid) {

      this.applicationForm.markAllAsTouched();

      return;
    }

    const application: JobApplication = {

      company:
        this.applicationForm.value.company || '',

      role:
        this.applicationForm.value.role || '',

      date:
        this.applicationForm.value.date || '',

      status:
        this.applicationForm.value.status || 'Applied'

    };

    this.applicationService
      .addApplication(application)
      .subscribe({

        next: (savedApplication) => {

          this.applications = [
            ...this.applications,
            savedApplication
          ];

          this.applicationForm.reset({

            company: '',

            role: '',

            date: '',

            status: 'Applied'

          });

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to add application:',
            error
          );

          this.errorMessage =
            'Failed to add application. Please try again.';

          this.cdr.detectChanges();

        }

      });
  }

  updateStatus(
    id: number | undefined,
    event: Event
  ): void {

    if (id === undefined) {

      return;
    }

    const selectElement =
      event.target as HTMLSelectElement;

    const status =
      selectElement.value;

    this.applicationService
      .updateApplicationStatus(
        id,
        status
      )
      .subscribe({

        next: (updatedApplication) => {

          this.applications =
            this.applications.map(
              application =>
                application.id === id
                  ? updatedApplication
                  : application
            );

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to update application status:',
            error
          );

          this.errorMessage =
            'Failed to update application status. Please try again.';

          this.cdr.detectChanges();

        }

      });
  }

  deleteApplication(
    id: number | undefined
  ): void {

    if (id === undefined) {

      return;
    }

    this.applicationService
      .deleteApplication(id)
      .subscribe({

        next: () => {

          this.applications =
            this.applications.filter(
              application =>
                application.id !== id
            );

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to delete application:',
            error
          );

          this.errorMessage =
            'Failed to delete application. Please try again.';

          this.cdr.detectChanges();

        }

      });
  }

}


