import {
  Component,
  ChangeDetectorRef,
  OnInit
} from '@angular/core';

import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  SkillAnalysisService,
  SkillAnalysis
} from '../../services/skill-analysis';

@Component({
  selector: 'app-skill-analyzer',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './skill-analyzer.html',
  styleUrl: './skill-analyzer.css'
})
export class SkillAnalyzer implements OnInit {

  jobSkills = '';

  mySkills = '';

  matchedSkills: string[] = [];

  missingSkills: string[] = [];

  matchPercentage = 0;

  isLoading = false;

  errorMessage = '';

  previousAnalyses: SkillAnalysis[] = [];

  constructor(
    private skillAnalysisService: SkillAnalysisService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    this.loadPreviousAnalyses();

  }

  analyzeSkills(): void {

    if (
      !this.jobSkills.trim() ||
      !this.mySkills.trim()
    ) {

      this.errorMessage =
        'Please enter both job skills and your skills.';

      return;
    }

    this.errorMessage = '';

    this.isLoading = true;

    const analysis: SkillAnalysis = {

      jobSkills: this.jobSkills,

      mySkills: this.mySkills,

      matchedSkills: '',

      missingSkills: '',

      matchPercentage: 0

    };

    this.skillAnalysisService
      .analyzeSkills(analysis)
      .subscribe({

        next: (result) => {

          this.matchedSkills =
            result.matchedSkills
              ? result.matchedSkills
                  .split(',')
                  .map(skill => skill.trim())
                  .filter(skill => skill.length > 0)
              : [];

          this.missingSkills =
            result.missingSkills
              ? result.missingSkills
                  .split(',')
                  .map(skill => skill.trim())
                  .filter(skill => skill.length > 0)
              : [];

          this.matchPercentage =
            result.matchPercentage;

          this.isLoading = false;

          this.previousAnalyses = [
            result,
            ...this.previousAnalyses
          ];

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to analyze skills:',
            error
          );

          this.errorMessage =
            'Failed to analyze skills. Please try again.';

          this.isLoading = false;

          this.cdr.detectChanges();

        }

      });

  }

  loadPreviousAnalyses(): void {

    this.skillAnalysisService
      .getAnalyses()
      .subscribe({

        next: (analyses) => {

          this.previousAnalyses =
            analyses;

          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error(
            'Failed to load previous analyses:',
            error
          );

        }

      });

  }

}


