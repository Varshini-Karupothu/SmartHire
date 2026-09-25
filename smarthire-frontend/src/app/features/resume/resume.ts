import {
  Component,
  ChangeDetectorRef,
  OnInit
} from '@angular/core';

import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  ResumeService,
  Resume as ResumeModel
} from '../../services/resume';

@Component({
  selector: 'app-resume',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './resume.html',
  styleUrl: './resume.css'
})
export class Resume implements OnInit {

  resume: ResumeModel = {

    name: '',
    email: '',
    phone: '',
    location: '',
    summary: '',
    education: '',
    skills: '',
    projects: '',
    certifications: '',
    experience: ''

  };

  saved = false;

  isLoading = false;

  errorMessage = '';

  constructor(
    private resumeService: ResumeService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.loadResume();

  }

  loadResume(): void {

    this.isLoading = true;

    this.errorMessage = '';

    this.resumeService
      .getResume()
      .subscribe({

        next: (savedResume) => {

          if (savedResume) {

            this.resume = savedResume;

            this.saved = true;

          }

          this.isLoading = false;

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to load resume:',
            error
          );

          this.isLoading = false;

          this.cdr.detectChanges();

        }

      });

  }

  saveResume(): void {

    this.resumeService
      .saveResume(this.resume)
      .subscribe({

        next: (savedResume) => {

          console.log(
            'Resume saved:',
            savedResume
          );

          this.resume = savedResume;

          this.saved = true;

          this.errorMessage = '';

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to save resume:',
            error
          );

          this.errorMessage =
            'Failed to save resume. Please try again.';

          this.cdr.detectChanges();

        }

      });

  }

  editResume(): void {

    this.saved = false;

  }

}