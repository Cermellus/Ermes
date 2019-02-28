import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IErmesUser } from 'app/shared/model/ermes-user.model';
import { ErmesUserService } from './ermes-user.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-ermes-user-update',
    templateUrl: './ermes-user-update.component.html'
})
export class ErmesUserUpdateComponent implements OnInit {
    ermesUser: IErmesUser;
    isSaving: boolean;

    roles: IRole[];

    companies: ICompany[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected ermesUserService: ErmesUserService,
        protected roleService: RoleService,
        protected companyService: CompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ ermesUser }) => {
            this.ermesUser = ermesUser;
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
        if (this.ermesUser.id !== undefined) {
            this.subscribeToSaveResponse(this.ermesUserService.update(this.ermesUser));
        } else {
            this.subscribeToSaveResponse(this.ermesUserService.create(this.ermesUser));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IErmesUser>>) {
        result.subscribe((res: HttpResponse<IErmesUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
