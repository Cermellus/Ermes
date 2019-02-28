import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditReason } from 'app/shared/model/credit-reason.model';
import { CreditReasonService } from './credit-reason.service';

@Component({
    selector: 'jhi-credit-reason-delete-dialog',
    templateUrl: './credit-reason-delete-dialog.component.html'
})
export class CreditReasonDeleteDialogComponent {
    creditReason: ICreditReason;

    constructor(
        protected creditReasonService: CreditReasonService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditReasonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditReasonListModification',
                content: 'Deleted an creditReason'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-reason-delete-popup',
    template: ''
})
export class CreditReasonDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditReason }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditReasonDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditReason = creditReason;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/credit-reason', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/credit-reason', { outlets: { popup: null } }]);
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
