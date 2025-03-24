import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect, jest } from '@jest/globals';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { AuthService } from './features/auth/services/auth.service';
import { SessionService } from './services/session.service';
import { Router } from '@angular/router';

describe('AppComponent', () => {
  let fixture;
  let app: AppComponent;
  let sessionServiceMock: jest.Mocked<SessionService>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(async () => {
    sessionServiceMock = {
      $isLogged: jest.fn(),
      logOut: jest.fn(),
    } as unknown as jest.Mocked<SessionService>;

    routerMock = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  it('should call sessionService.logOut and navigate when logout is called', () => {
    const spyLogOut = jest.spyOn(sessionServiceMock, 'logOut');
    const spyNavigate = jest.spyOn(routerMock, 'navigate');

    app.logout();

    expect(spyLogOut).toHaveBeenCalled();
    expect(spyNavigate).toHaveBeenCalledWith(['']);
  });

  it('should return an observable boolean from $isLogged', () => {
    const mockIsLogged = true;
    sessionServiceMock.$isLogged.mockReturnValue(of(mockIsLogged));

    const result = app.$isLogged();

    result.subscribe(value => {
      expect(value).toBe(mockIsLogged);
    });
  });
});
