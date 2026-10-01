import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface JobApplication {
  id?: number;
  company: string;
  role: string;
  date: string;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  private apiUrl =
    `${environment.apiUrl}/api/applications`;

  constructor(private http: HttpClient) {}

  getApplications(): Observable<JobApplication[]> {

    return this.http.get<JobApplication[]>(
      this.apiUrl
    );

  }

  addApplication(
    application: JobApplication
  ): Observable<JobApplication> {

    return this.http.post<JobApplication>(
      this.apiUrl,
      application
    );

  }

  updateApplicationStatus(
    id: number,
    status: string
  ): Observable<JobApplication> {

    return this.http.put<JobApplication>(
      `${this.apiUrl}/${id}/status`,
      { status }
    );

  }

  deleteApplication(
    id: number
  ): Observable<string> {

    return this.http.delete(
      `${this.apiUrl}/${id}`,
      {
        responseType: 'text'
      }
    );

  }

}