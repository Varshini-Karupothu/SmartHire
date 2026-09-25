import { Component, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  MockInterviewService,
  MockQuestion,
  MockAnswerRequest,
  MockEvaluationResponse,
  QuestionResult
} from '../../services/mock-interview.service';

@Component({
  selector: 'app-mock-interview',
  imports: [FormsModule, RouterLink],
  templateUrl: './mock-interview.html',
  styleUrl: './mock-interview.css'
})
export class MockInterview {

  role = '';
  difficulty = '';
  topic = '';

  started = false;
  completed = false;
  loading = false;
  evaluating = false;

  errorMessage = '';

  currentQuestion = 0;
  score = 0;

  questions: MockQuestion[] = [];

  results: QuestionResult[] = [];


  constructor(
    private mockInterviewService: MockInterviewService,
    private cdr: ChangeDetectorRef
  ) {}


  // ================================
  // START INTERVIEW
  // ================================

  startInterview(): void {

    console.log('START INTERVIEW CLICKED');

    if (!this.role || !this.difficulty || !this.topic) {

      this.errorMessage =
        'Please select all options.';

      return;
    }

    this.loading = true;
    this.started = false;
    this.completed = false;
    this.evaluating = false;

    this.errorMessage = '';

    this.questions = [];
    this.results = [];

    this.currentQuestion = 0;
    this.score = 0;


    this.mockInterviewService
      .getQuestions(
        this.role,
        this.difficulty,
        this.topic
      )
      .subscribe({

        next: (data: MockQuestion[]) => {

          console.log(
            'Questions received:',
            data
          );


          if (!data || data.length === 0) {

            this.loading = false;

            this.errorMessage =
              'No questions found for the selected options.';

            this.cdr.detectChanges();

            return;
          }


          this.questions = data.map(
            (question: MockQuestion) => ({

              id: question.id,

              role: question.role,

              difficulty: question.difficulty,

              topic: question.topic,

              question: question.question,

              answer: ''

            })
          );


          this.currentQuestion = 0;

          this.score = 0;

          this.loading = false;

          this.started = true;

          this.completed = false;


          console.log(
            'Questions stored:',
            this.questions
          );


          this.cdr.detectChanges();
        },


        error: (error) => {

          console.error(
            'Failed to load questions:',
            error
          );

          this.loading = false;

          this.started = false;

          this.errorMessage =
            'Unable to load interview questions. Please try again.';

          this.cdr.detectChanges();
        }

      });
  }


  // ================================
  // NEXT QUESTION
  // ================================

  nextQuestion(): void {

    if (this.questions.length === 0) {
      return;
    }


    if (
      this.currentQuestion <
      this.questions.length - 1
    ) {

      this.currentQuestion++;

    } else {

      this.finishInterview();
    }
  }


  // ================================
  // FINISH INTERVIEW
  // ================================

  finishInterview(): void {

    if (
      this.questions.length === 0 ||
      this.evaluating
    ) {

      return;
    }


    this.evaluating = true;

    this.errorMessage = '';


    const answers: MockAnswerRequest[] =
      this.questions
        .filter(
          question =>
            question.id !== undefined
        )
        .map(
          question => ({

            questionId: question.id!,

            answer: question.answer || ''

          })
        );


    console.log(
      'Submitting answers:',
      answers
    );


    this.mockInterviewService
      .evaluateAnswers(answers)
      .subscribe({

        next: (
          response: MockEvaluationResponse
        ) => {

          console.log(
            'Evaluation response:',
            response
          );


          this.score = response.score;

          this.results = response.results;


          this.evaluating = false;

          this.completed = true;


          this.cdr.detectChanges();
        },


        error: (error) => {

          console.error(
            'Failed to evaluate answers:',
            error
          );


          this.evaluating = false;


          this.errorMessage =
            'Unable to calculate your score. Please try again.';


          this.cdr.detectChanges();
        }

      });
  }


  // ================================
  // GET QUESTION RESULT
  // ================================

  getResult(
    questionId: number | undefined
  ): QuestionResult | undefined {

    if (questionId === undefined) {

      return undefined;
    }


    return this.results.find(
      result =>
        result.questionId === questionId
    );
  }


  // ================================
  // CHECK MISSING KEYWORDS
  // ================================

  hasMissingKeywords(
    questionId: number | undefined
  ): boolean {

    const result =
      this.getResult(questionId);


    return !!result &&
      result.missingKeywords.length > 0;
  }


  // ================================
  // GET SCORE MESSAGE
  // ================================

  getScoreMessage(): string {

    if (this.score >= 80) {

      return 'Excellent performance! Keep it up.';
    }


    if (this.score >= 60) {

      return 'Good performance. A little more practice will help.';
    }


    if (this.score >= 40) {

      return 'Fair attempt. Review the missing concepts and practice more.';
    }


    return 'Keep practicing. Review the important concepts and try again.';
  }


  // ================================
  // RESTART INTERVIEW
  // ================================

  restartInterview(): void {

    this.role = '';

    this.difficulty = '';

    this.topic = '';


    this.started = false;

    this.completed = false;

    this.loading = false;

    this.evaluating = false;


    this.errorMessage = '';


    this.currentQuestion = 0;

    this.score = 0;


    this.questions = [];

    this.results = [];


    this.cdr.detectChanges();
  }

}

