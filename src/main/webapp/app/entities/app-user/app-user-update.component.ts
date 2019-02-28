import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAppUser } from 'app/shared/model/app-user.model';
import { AppUserService } from './app-user.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-app-user-update',
    templateUrl: './app-user-update.component.html'
})
export class AppUserUpdateComponent implements OnInit {
    appUser: IAppUser;
    isSaving: boolean;

    roles: IRole[];

    companies: ICompany[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected appUserService: AppUserService,
        protected roleService: RoleService,
        protected companyService: CompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appUser }) => {
            this.appUser = appUser;
        });
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.companyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompany[]>) => response.body)
            )
            .subscribe((res: ICompany[]) => (this.companies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.appUser.id !== undefined) {
            this.subscribeToSaveResponse(this.appUserService.update(this.appUser));
        } else {
            this.subscribeToSaveResponse(this.appUserService.create(this.appUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppUser>>) {
        result.subscribe((res: HttpResponse<IAppUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }
}
