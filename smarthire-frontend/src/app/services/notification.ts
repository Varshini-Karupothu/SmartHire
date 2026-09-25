import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Notification {
  id?: number;
  title: string;
  message: string;
  read: boolean;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private apiUrl =
    'http://localhost:8080/api/notifications';

  constructor(private http: HttpClient) {}

  getNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(
      this.apiUrl
    );
  }

  createNotification(
    title: string,
    message: string
  ): Observable<Notification> {

    return this.http.post<Notification>(
      `${this.apiUrl}?title=${encodeURIComponent(title)}&message=${encodeURIComponent(message)}`,
      {}
    );
  }

  markAsRead(
    id: number
  ): Observable<Notification> {

    return this.http.put<Notification>(
      `${this.apiUrl}/${id}/read`,
      {}
    );
  }
}