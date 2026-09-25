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
  InterviewService,
  Interview
} from '../../services/interview';

@Component({
  selector: 'app-interviews',
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './interviews.html',
  styleUrl: './interviews.css'
})
export class Interviews {

  interviewForm;

  interviews: Interview[] = [];

  isLoading = false;

  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private interviewService: InterviewService,
    private cdr: ChangeDetectorRef
  ) {

    this.interviewForm = this.fb.group({

      company: [
        '',
        Validators.required
      ],

      role: [
        '',
        Validators.required
      ],

      round: [
        'Technical Round',
        Validators.required
      ],

      date: [
        '',
        Validators.required
      ],

      time: [
        '',
        Validators.required
      ],

      feedback: [
        ''
      ]

    });

    this.loadInterviews();
  }


  loadInterviews(): void {

    this.isLoading = true;

    this.errorMessage = '';

    this.interviewService
      .getInterviews()
      .subscribe({

        next: (interviews) => {

          console.log(
            'Interviews loaded:',
            interviews
          );

          this.interviews = interviews;

          this.isLoading = false;

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to load interviews:',
            error
          );

          this.errorMessage =
            'Failed to load interviews. Please try again.';

          this.isLoading = false;

          this.cdr.detectChanges();

        }

      });

  }


  addInterview(): void {

    if (this.interviewForm.invalid) {

      this.interviewForm.markAllAsTouched();

      return;
    }

    const interview: Interview = {

      company:
        this.interviewForm.value.company || '',

      role:
        this.interviewForm.value.role || '',

      round:
        this.interviewForm.value.round ||
        'Technical Round',

      date:
        this.interviewForm.value.date || '',

      time:
        this.interviewForm.value.time || '',

      feedback:
        this.interviewForm.value.feedback || ''

    };


    this.interviewService
      .addInterview(interview)
      .subscribe({

        next: (savedInterview) => {

          console.log(
            'Interview saved:',
            savedInterview
          );

          this.interviews = [
            ...this.interviews,
            savedInterview
          ];

          this.interviewForm.reset({

            company: '',

            role: '',

            round: 'Technical Round',

            date: '',

            time: '',

            feedback: ''

          });

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to add interview:',
            error
          );

          this.errorMessage =
            'Failed to add interview. Please try again.';

          this.cdr.detectChanges();

        }

      });

  }


  deleteInterview(
    id: number | undefined
  ): void {

    if (id === undefined) {

      return;
    }

    this.interviewService
      .deleteInterview(id)
      .subscribe({

        next: () => {

          console.log(
            'Interview deleted:',
            id
          );

          this.interviews =
            this.interviews.filter(
              interview =>
                interview.id !== id
            );

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to delete interview:',
            error
          );

          this.errorMessage =
            'Failed to delete interview. Please try again.';

          this.cdr.detectChanges();

        }

      });

  }

}