import {LoginService} from './services/login.service';
import {MessageService} from './services/message.service';
import {AuthConfig, NullValidationHandler, OAuthService} from 'angular-oauth2-oidc';
import {Component} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'meetups-challenge-angular-web-ui';

  username: string | undefined;
  isLogged: boolean | undefined;
  isAdmin: boolean | undefined;

  authConfig: AuthConfig = {
    issuer: 'http://localhost:8082/auth/realms/MeetupsChallenge',
    redirectUri: window.location.origin,
    clientId: 'java-meetups-challenge-api-public',
    responseType: 'code',
    scope: 'openid profile email offline_access',
    showDebugInformation: true,
  };

  constructor(
    private oauthService: OAuthService,
    private messageService: MessageService,
    private loginService: LoginService
  ) {
    this.configure();
  }

  configure(): void {
    this.oauthService.configure(this.authConfig);
    this.oauthService.tokenValidationHandler = new NullValidationHandler();
    this.oauthService.setupAutomaticSilentRefresh();
    this.oauthService.loadDiscoveryDocument().then(() => this.oauthService.tryLogin())
      .then(() => {
        if (this.oauthService.hasValidIdToken() && this.oauthService.getIdentityClaims()) {
          this.isLogged = this.loginService.getIsLogged();
          this.isAdmin = this.loginService.getIsAdmin();
          this.username = this.loginService.getUsername();
          this.messageService.sendMessage(this.loginService.getUsername());
        } else {
          this.messageService.sendMessage('');
        }
      });
  }

}
