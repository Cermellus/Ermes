import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditRequestStatus } from 'app/shared/model/credit-request-status.model';
import { CreditRequestStatusService } from './credit-request-status.service';

@Component({
    selector: 'jhi-credit-request-status-delete-dialog',
    templateUrl: './credit-request-status-delete-dialog.component.html'
})
export class CreditRequestStatusDeleteDialogComponent {
    creditRequestStatus: ICreditRequestStatus;

    constructor(
        protected creditRequestStatusService: CreditRequestStatusService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditRequestStatusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditRequestStatusListModification',
                content: 'Deleted an creditRequestStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-request-status-delete-popup',
    template: ''
})
export class CreditRequestStatusDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditRequestStatus }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditRequestStatusDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditRequestStatus = creditRequestStatus;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/credit-request-status', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/credit-request-status', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
