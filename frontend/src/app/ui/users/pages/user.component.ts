import { Component, inject } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { userService } from '../user.service';

@Component({
  selector: 'app-user',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './user.component.html',
})
export class user {
  private readonly userServ = inject(userService);

  readonly user$ = this.userServ.fetchMe();
}
