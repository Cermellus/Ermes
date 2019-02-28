import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICreditRequest } from 'app/shared/model/credit-request.model';
import { CreditRequestService } from './credit-request.service';

@Component({
    selector: 'jhi-credit-request-delete-dialog',
    templateUrl: './credit-request-delete-dialog.component.html'
})
export class CreditRequestDeleteDialogComponent {
    creditRequest: ICreditRequest;

    constructor(
        protected creditRequestService: CreditRequestService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditRequestService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'creditRequestListModification',
                content: 'Deleted an creditRequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-request-delete-popup',
    template: ''
})
export class CreditRequestDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ creditRequest }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CreditRequestDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.creditRequest = creditRequest;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/credit-request', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/credit-request', { outlets: { popup: null } }]);
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
