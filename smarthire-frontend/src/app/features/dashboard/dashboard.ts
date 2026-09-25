import {
  Component,
  ChangeDetectorRef,
  OnInit
} from '@angular/core';

import {
  Router,
  RouterLink
} from '@angular/router';

import {
  ApplicationService,
  JobApplication
} from '../../services/application';

import {
  InterviewService,
  Interview
} from '../../services/interview';

import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {

  applications: JobApplication[] = [];
  interviews: Interview[] = [];

  recentApplications: JobApplication[] = [];
  upcomingInterviews: Interview[] = [];

  totalApplications = 0;
  totalInterviews = 0;
  selectedApplications = 0;
  rejectedApplications = 0;

  constructor(
    private applicationService: ApplicationService,
    private interviewService: InterviewService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadApplications();
    this.loadInterviews();
  }

  loadApplications(): void {
    this.applicationService
      .getApplications()
      .subscribe({
        next: (applications) => {

          console.log(
            'DASHBOARD APPLICATIONS:',
            applications
          );

          this.applications = applications;

          this.totalApplications =
            applications.length;

          this.selectedApplications =
            applications.filter(
              application =>
                application.status === 'Selected'
            ).length;

          this.rejectedApplications =
            applications.filter(
              application =>
                application.status === 'Rejected'
            ).length;

          this.recentApplications =
            applications.slice(0, 5);

          console.log(
            'DASHBOARD TOTAL:',
            this.totalApplications
          );

          this.cdr.detectChanges();
        },

        error: (error) => {
          console.error(
            'Dashboard applications error:',
            error
          );
        }
      });
  }

  loadInterviews(): void {
    this.interviewService
      .getInterviews()
      .subscribe({
        next: (interviews) => {

          console.log(
            'DASHBOARD INTERVIEWS:',
            interviews
          );

          this.interviews = interviews;

          this.totalInterviews =
            interviews.length;

          this.upcomingInterviews =
            interviews.filter(
              interview =>
                this.isUpcoming(interview.date)
            );

          console.log(
            'UPCOMING INTERVIEWS:',
            this.upcomingInterviews
          );

          this.cdr.detectChanges();
        },

        error: (error) => {
          console.error(
            'Dashboard interviews error:',
            error
          );
        }
      });
  }

  isUpcoming(date: string): boolean {

    if (!date) {
      return false;
    }

    const interviewDate =
      new Date(date);

    if (isNaN(interviewDate.getTime())) {
      return false;
    }

    const today =
      new Date();

    today.setHours(
      0,
      0,
      0,
      0
    );

    interviewDate.setHours(
      0,
      0,
      0,
      0
    );

    return interviewDate >= today;
  }

  logout(): void {

    this.authService.logout();

    this.router.navigate([
      '/login'
    ]);
  }
}
