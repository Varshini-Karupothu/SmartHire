import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Resume {
  id?: number;
  name: string;
  email: string;
  phone: string;
  location: string;
  summary: string;
  education: string;
  skills: string;
  projects: string;
  certifications: string;
  experience: string;
}

@Injectable({
  providedIn: 'root'
})
export class ResumeService {

  private apiUrl =
    'http://localhost:8080/api/resume';

  constructor(private http: HttpClient) {}

  getResume(): Observable<Resume> {

    return this.http.get<Resume>(
      this.apiUrl
    );

  }

  saveResume(
    resume: Resume
  ): Observable<Resume> {

    return this.http.post<Resume>(
      this.apiUrl,
      resume
    );

  }

}