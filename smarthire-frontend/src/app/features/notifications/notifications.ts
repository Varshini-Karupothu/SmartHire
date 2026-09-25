import {
  Component,
  ChangeDetectorRef,
  OnInit
} from '@angular/core';

import { RouterLink } from '@angular/router';

import {
  NotificationService,
  Notification
} from '../../services/notification';

@Component({
  selector: 'app-notifications',
  imports: [RouterLink],
  templateUrl: './notifications.html',
  styleUrl: './notifications.css'
})
export class Notifications implements OnInit {

  notifications: Notification[] = [];

  isLoading = false;

  errorMessage = '';

  constructor(
    private notificationService: NotificationService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.loadNotifications();
  }

  loadNotifications(): void {

    this.isLoading = true;
    this.errorMessage = '';

    this.notificationService
      .getNotifications()
      .subscribe({
        next: (notifications) => {

          this.notifications =
            notifications;

          this.isLoading = false;

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error(
            'Failed to load notifications:',
            error
          );

          this.errorMessage =
            'Failed to load notifications.';

          this.isLoading = false;

          this.cdr.detectChanges();
        }
      });
  }

  markAsRead(
    notification: Notification
  ): void {

    if (!notification.id || notification.read) {
      return;
    }

    this.notificationService
      .markAsRead(notification.id)
      .subscribe({
        next: (updatedNotification) => {

          notification.read =
            updatedNotification.read;

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error(
            'Failed to mark notification as read:',
            error
          );
        }
      });
  }
}

